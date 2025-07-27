package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.dto.response.GiftBundleResponseDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface GiftBundleService {
    /**
     * 특정 사용자의 선물 꾸러미 목록 조회
     * @param memberId
     * @param sort 정렬 정보
     * @return 선물 담긴 꾸러미 목록
     */
    List<GiftBundleResponseDto> getMyGiftBundles(Long memberId, Sort sort);
}
