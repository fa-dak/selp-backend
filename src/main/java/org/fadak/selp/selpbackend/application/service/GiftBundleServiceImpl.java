package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.domain.dto.response.GiftBundleResponseDto;
import org.fadak.selp.selpbackend.domain.entity.GiftBundle;
import org.fadak.selp.selpbackend.domain.entity.GiftBundleItem;
import org.fadak.selp.selpbackend.domain.entity.Product;
import org.fadak.selp.selpbackend.domain.repository.GiftBundleItemRepository;
import org.fadak.selp.selpbackend.domain.repository.GiftBundleRepository;
import org.fadak.selp.selpbackend.domain.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftBundleServiceImpl implements GiftBundleService {
    private final GiftBundleRepository giftBundleRepository;
    private final GiftBundleItemRepository giftBundleItemRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GiftBundleResponseDto> getMyGiftBundles(Long memberId, Sort sort) {

        // fetch join 으로 giftBundle, event, receiverInfo 조회
        List<GiftBundle> giftBundles = giftBundleRepository.findAllByMemberIdWithDetails(memberId, sort);
        if(giftBundles.isEmpty()){
            return List.of();
        }

        // GiftBundle ID 목록 추출
        List<Long> giftBundleIds = giftBundles.stream().map(GiftBundle::getId).toList();

        // 모든 GiftBundleItem을 IN 쿼리로 한번에 조회
        List<GiftBundleItem> giftBundleItems = giftBundleItemRepository.findAllByGiftBundleIdIn(giftBundleIds);

        // GiftBundleItem 에서 ProductId 목록 추출 후 중복제거
        List<Long> productIds = giftBundleItems.stream().map(GiftBundleItem::getProductId).distinct().toList();

        // 모든 관련된 Product IN 쿼리로 한번에 조회
        List<Product> products = productRepository.findAllById(productIds);

        // 메모리에서 데이터 조합을 위한 Map 생성
        Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, p -> p));
        Map<Long, List<GiftBundleResponseDto.ProductDto>> itemsByBundleId = giftBundleItems.stream().collect(Collectors.groupingBy(
                item -> item.getGiftBundle().getId(),
                Collectors.mapping(item ->
                        GiftBundleResponseDto.ProductDto.from(productMap.get(item.getProductId())), Collectors.toList()
                )
        ));

        // DTO 조립
        return giftBundles.stream().map(giftBundle -> GiftBundleResponseDto.from(
                giftBundle,
                itemsByBundleId.getOrDefault(giftBundle.getId(), List.of())
        )).toList();
    }
}
