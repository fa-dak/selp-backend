package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleRecommendRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleSaveRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftRecommendAgainRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.GiftBundleItemResponseDto;

import java.util.List;

public interface GiftBundleFacadeService {
    public List<GiftBundleItemResponseDto> recommendGiftBundle(GiftBundleRecommendRequestDto requestDto);

    public GiftBundleItemResponseDto recommendGiftBundleItem(GiftRecommendAgainRequestDto requestDto);

    public void registerGiftBundle(GiftBundleSaveRequestDto requestDto, Long memberId);

}