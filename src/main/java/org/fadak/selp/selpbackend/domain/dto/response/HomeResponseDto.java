package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeResponseDto {

    private List<EventPreviewDto> upcomingEvents;
    private List<ProductPreviewDto> recommendProducts;
    private GiftBundleResponseDto recentGiftBundleProducts;

    public static HomeResponseDto of(List<EventPreviewDto> events, List<ProductPreviewDto> products, GiftBundleResponseDto recentGiftBundleProducts) {
        return HomeResponseDto.builder()
                .upcomingEvents(events)
                .recommendProducts(products)
                .recentGiftBundleProducts(recentGiftBundleProducts)
                .build();
    }
}