package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleRecommendRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.GiftBundleRecommendResponseDto;

public interface GiftBundleFacadeService {
    public GiftBundleRecommendResponseDto recommendGiftBundle(GiftBundleRecommendRequestDto requestDto);
}