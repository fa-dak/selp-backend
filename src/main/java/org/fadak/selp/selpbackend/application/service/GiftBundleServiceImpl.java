package org.fadak.selp.selpbackend.application.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.domain.dto.response.GiftBundleResponseDto;
import org.fadak.selp.selpbackend.domain.entity.GiftBundle;
import org.fadak.selp.selpbackend.domain.repository.GiftBundleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GiftBundleServiceImpl implements GiftBundleService {

    private final GiftBundleRepository giftBundleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GiftBundleResponseDto> getMyGiftBundles(Long memberId, Sort sort) {
        // 1. Fetch Join 쿼리를 호출하여 모든 관련 데이터를 한 번에 가져옴
        List<GiftBundle> giftBundles = giftBundleRepository.findAllByMemberIdWithDetails(memberId,
            sort);

        // 2. DTO로 변환
        return giftBundles.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GiftBundleResponseDto getMyGiftBundleDetail(Long bundleId, Long memberId) {
        // 1. Fetch Join 쿼리를 호출하여 모든 관련 데이터를 한 번에 가져옴
        GiftBundle giftBundle = giftBundleRepository.findDetailsByIdAndMemberId(bundleId, memberId)
            .orElseThrow(() -> new NoSuchElementException("해당 선물 꾸러미를 찾을 수 없습니다."));

        // 2. DTO로 변환
        return convertToDto(giftBundle);
    }

    @Override
    public GiftBundle searchById(Long giftBundleId, long loginMemberId) {

        return giftBundleRepository.findByIdAndMember_Id(giftBundleId, loginMemberId)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public GiftBundle save(GiftBundle giftBundle) {

        return giftBundleRepository.save(giftBundle);
    }

    /**
     * GiftBundle 엔티티를 GiftBundleResponseDto로 변환하는 헬퍼 메소드.
     */
    private GiftBundleResponseDto convertToDto(GiftBundle giftBundle) {
        // ProductDto 리스트 생성
        List<GiftBundleResponseDto.ProductDto> productDtos = giftBundle.getGiftBundleItems()
            .stream()
            .map(item -> GiftBundleResponseDto.ProductDto.from(item.getProduct()))
            .collect(Collectors.toList());

        // 최종 DTO 조립
        return GiftBundleResponseDto.from(giftBundle, productDtos);
    }
}

