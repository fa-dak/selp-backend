package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.util.OpenAiBuilderUtil;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleRecommendRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleSaveRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftRecommendAgainRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.GiftBundleItemResponseDto;
import org.fadak.selp.selpbackend.domain.entity.Member;
import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;
import org.fadak.selp.selpbackend.domain.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class GiftBundleFacadeServiceImpl implements GiftBundleFacadeService {

    private final CategoryInferenceService categoryInferenceService;
    private final ElasticEmbeddingSearchService embeddingSearcher;

    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;
    private final ReceiverInfoRepository receiverInfoRepository;
    private final GiftBundleRepository giftBundleRepository;
    private final GiftBundleItemRepository giftBundleItemRepository;

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

        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        ReceiverInfo receiverInfo =
    }
}
