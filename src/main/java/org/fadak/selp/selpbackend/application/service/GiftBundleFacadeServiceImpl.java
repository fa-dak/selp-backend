package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.application.util.OpenAiBuilderUtil;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleRecommendRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleSaveFromCalendarRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleSaveRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftRecommendAgainRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.GiftBundleItemResponseDto;
import org.fadak.selp.selpbackend.domain.entity.*;
import org.fadak.selp.selpbackend.domain.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftBundleFacadeServiceImpl implements GiftBundleFacadeService {

    private final CategoryInferenceService categoryInferenceService;
    private final ElasticEmbeddingSearchService embeddingSearcher;

    private final MemberRepository memberRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final EventRepository eventRepository;
    private final ReceiverInfoRepository receiverInfoRepository;
    private final GiftBundleRepository giftBundleRepository;
    private final GiftBundleItemRepository giftBundleItemRepository;
    private final ProductRepository productRepository;
    private final PreferenceRepository preferenceRepository;

    @Override
    public List<GiftBundleItemResponseDto> recommendGiftBundle(GiftBundleRecommendRequestDto requestDto) {

        // 사용자의 입력 데이터를 바탕으로 카테고리별 금액을 산정
        Map<String, Integer> categoryBudgetMap = categoryInferenceService.distributeBudget(requestDto);
        List<GiftBundleItemResponseDto> result = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : categoryBudgetMap.entrySet()) {
            String category = entry.getKey();
            Integer budget = entry.getValue();

            String textForEmbedding = OpenAiBuilderUtil.buildEmbeddingPrompt(requestDto, category, budget);

            List<Map<String, Object>> searchResults = embeddingSearcher.searchByUserInput(textForEmbedding, 1, category, budget);

            for (Map<String, Object> product : searchResults) {
                GiftBundleItemResponseDto item = GiftBundleItemResponseDto.builder()
                        .id(Long.valueOf((String) product.get("product_id")))
                        .name((String) product.get("name"))
                        .price(Long.valueOf((Integer) product.get("price")))
                        .category((String) product.get("category"))
                        .imagePath((String) product.get("image_path"))
                        .detailPath((String) product.get("detail_path"))
                        .build();

                result.add(item);
            }
        }

        return result;
    }

    @Override
    public GiftBundleItemResponseDto recommendGiftBundleItem(GiftRecommendAgainRequestDto requestDto) {
        GiftBundleRecommendRequestDto dto = requestDto.toGiftBundleRecommendRequestDto();
        String textForEmbedding = OpenAiBuilderUtil.buildEmbeddingPrompt(dto, requestDto.getCategory(), requestDto.getPrice());

        Map<String, Object> searchResult = embeddingSearcher.recommendSimilarProduct(textForEmbedding, 1, requestDto.getCategory(), requestDto.getPrice(), requestDto.getProductId());

        if (searchResult == null) {
            throw new IllegalArgumentException("상품 재추천 오류");
        }

        return GiftBundleItemResponseDto.builder()
                .id(Long.valueOf((String) searchResult.get("product_id")))
                .name((String) searchResult.get("name"))
                .category((String) searchResult.get("category"))
                .price(Long.valueOf((Integer) searchResult.get("price")))
                .imagePath((String) searchResult.get("image_path"))
                .detailPath((String) searchResult.get("detail_path"))
                .build();
    }

    @Override
    @Transactional
    public void registerGiftBundle(GiftBundleSaveRequestDto requestDto, Long memberId) {

        log.info("=== 꾸러미 저장 시작 ===");
        log.info("memberId: {}", memberId);
        log.info("requestDto: {}", requestDto);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);


        ReceiverInfo receiverInfo = requestDto.toReceiverInfo(member);
        ReceiverInfo receiverInfoEntity = receiverInfoRepository.save(receiverInfo); // 주변인 정보 저장

        Event event = requestDto.toEvent(receiverInfoEntity);
        Event eventEntity = eventRepository.save(event); // 이벤트 저장

        // 주변인 선호 저장
        List<String> categories = requestDto.getCategories();
        List<ProductCategory> productCategories = productCategoryRepository.findByNameIn(categories);

        List<Preference> preferences = productCategories.stream()
                .map(category -> Preference.builder()
                        .receiverInfo(receiverInfoEntity)       // 이미 저장된 ReceiverInfo
                        .category(category)
                        .build())
                .toList();

        preferenceRepository.saveAll(preferences);


        // 꾸러미 저장
        GiftBundle giftBundle = GiftBundle.builder()
                .event(eventEntity)
                .member(member)
                .build();

        GiftBundle giftBundleEntity = giftBundleRepository.save(giftBundle);


        // 선물 내역 저장
        List<GiftBundleItem> itemEntities = requestDto.getGiftIds().stream()
                .map(productId -> {
                    Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new IllegalArgumentException("상품 없음: " + productId));

                    return GiftBundleItem.builder()
                            .giftBundle(giftBundleEntity)
                            .product(product)
                            .build();
                })
                .collect(Collectors.toList());

        giftBundleItemRepository.saveAll(itemEntities);
    }

    @Override
    @Transactional
    public void registerGiftBundleFromCalendar(GiftBundleSaveFromCalendarRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        Event event = eventRepository.findById(requestDto.getEventId())
                .orElseThrow(IllegalArgumentException::new);

        GiftBundle giftBundle = GiftBundle.builder()
                .event(event)
                .member(member)
                .build();

        GiftBundle giftBundleEntity = giftBundleRepository.save(giftBundle);

        // 선물 내역 저장
        List<GiftBundleItem> itemEntities = requestDto.getGiftIds().stream()
                .map(productId -> {
                    Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new IllegalArgumentException("상품 없음: " + productId));

                    return GiftBundleItem.builder()
                            .giftBundle(giftBundleEntity)
                            .product(product)
                            .build();
                })
                .collect(Collectors.toList());

        giftBundleItemRepository.saveAll(itemEntities);
    }
}
