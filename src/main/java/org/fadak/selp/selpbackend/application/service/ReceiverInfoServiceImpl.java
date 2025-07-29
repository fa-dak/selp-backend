package org.fadak.selp.selpbackend.application.service;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.dto.request.ReceiverModifyRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.ReceiverRegisterRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.ReceiverInfoListResponseDto;
import org.fadak.selp.selpbackend.domain.entity.Preference;
import org.fadak.selp.selpbackend.domain.entity.ProductCategory;
import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;
import org.fadak.selp.selpbackend.domain.repository.PreferenceRepository;
import org.fadak.selp.selpbackend.domain.repository.ProductCategoryRepository;
import org.fadak.selp.selpbackend.domain.repository.ReceiverInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiverInfoServiceImpl implements ReceiverInfoService {

    private final ReceiverInfoRepository repository;
    private final MemberService memberService;
    private final ProductCategoryRepository productCategoryRepository;
    private final PreferenceRepository preferenceRepository; // PreferenceRepository 주입

    @Override
    public List<ReceiverInfoListResponseDto> getReceiverInfoList(long memberId) {

        List<ReceiverInfo> receiverInfoList = repository.findAllByMemberId(memberId);

        return receiverInfoList.stream()
            .map(receiverInfo -> {
                List<String> categoryNames = receiverInfo.getPreferences()
                    .stream()
                    .map(p -> p.getCategory().getNameKR())
                    .toList();

                return ReceiverInfoListResponseDto.builder()
                    .receiverInfoId(receiverInfo.getId())
                    .receiverNickname(receiverInfo.getNickname())
                    .receiverAge(receiverInfo.getAge())
                    .receiverGender(receiverInfo.getGender())
                    .relationship(receiverInfo.getRelationship())
                    .receiverPreferences(categoryNames)
                    .receiverDetail(receiverInfo.getDetail())
                    .build();
            })
            .toList();
    }

    @Override
    @Transactional
    public void delete(long receiverInfoId, long loginMemberId) {

        repository.deleteByIdAndMember_Id(receiverInfoId, loginMemberId);
    }

    @Override
    @Transactional
    public void registerReceiverInfo(
        ReceiverRegisterRequestDto request,
        long loginMemberId
    ) {

        ReceiverInfo receiverInfo = ReceiverInfo.builder()
            .member(memberService.getMember(loginMemberId))
            .nickname(request.getNickname())
            .age(request.getAge())
            .gender(request.getGender())
            .relationship(request.getRelationship())
            .detail(request.getDetail())
            .build();
        repository.save(receiverInfo); // 먼저 ReceiverInfo를 저장하여 ID를 생성

        // preferenceIds를 기반으로 새로운 Preference 리스트를 생성하여 저장
        List<Preference> newPreferences = createPreferences(receiverInfo, request.getPreferenceIds());
        preferenceRepository.saveAll(newPreferences); // Preference를 별도로 저장
    }

    @Override
    @Transactional
    public void modifyReceiverInfo(
        ReceiverModifyRequestDto request,
        Long receiverInfoId,
        long loginMemberId
    ) {

        ReceiverInfo receiverInfo = repository.findByIdAndMember_Id(receiverInfoId, loginMemberId)
            .orElseThrow(IllegalArgumentException::new);

        receiverInfo.update(
            request.getNickname(),
            request.getAge(),
            request.getGender(),
            request.getRelationship(),
            request.getDetail()
        );
        repository.save(receiverInfo); // ReceiverInfo 변경 사항 저장

        // 1. 기존 Preference를 DB에서 직접 삭제
        preferenceRepository.deleteAllByReceiverInfoId(receiverInfoId);

        // 2. 새로 들어온 preferenceIds를 기반으로 새로운 Preference 리스트를 생성하여 저장
        List<Preference> newPreferences = createPreferences(receiverInfo, request.getPreferenceIds());
        preferenceRepository.saveAll(newPreferences);
    }

    private List<Preference> createPreferences(ReceiverInfo receiverInfo, List<Long> preferenceIds) {
        if (preferenceIds == null || preferenceIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProductCategory> categories = productCategoryRepository.findAllById(preferenceIds);

        if (categories.size() != preferenceIds.size()) {
            throw new IllegalArgumentException("하나 이상의 유효하지 않은 카테고리 ID가 포함되어 있습니다.");
        }

        return categories.stream()
            .map(category -> Preference.builder()
                .receiverInfo(receiverInfo)
                .category(category)
                .build())
            .toList();
    }


    @Override
    public ReceiverInfo getReceiverInfo(long receiverId) {

        return repository.findById(receiverId)
            .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public ReceiverInfoListResponseDto getReceiverInfoDetail(long receiverInfoId, long memberId) {

        ReceiverInfo receiverInfo = repository.findByIdAndMember_Id(receiverInfoId, memberId)
            .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        List<String> categoryNames = receiverInfo.getPreferences()
            .stream()
            .map(p -> p.getCategory().getName())
            .toList();

        return ReceiverInfoListResponseDto.builder()
            .receiverInfoId(receiverInfo.getId())
            .receiverNickname(receiverInfo.getNickname())
            .receiverAge(receiverInfo.getAge())
            .receiverGender(receiverInfo.getGender())
            .relationship(receiverInfo.getRelationship())
            .receiverPreferences(categoryNames)
            .receiverDetail(receiverInfo.getDetail())
            .build();
    }
}
