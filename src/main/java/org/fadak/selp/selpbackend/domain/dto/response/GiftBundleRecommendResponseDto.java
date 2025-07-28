package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class GiftBundleRecommendResponseDto {
    private Long id;
    private List<GiftBundleItemResponseDto> giftBundleItems;
}
