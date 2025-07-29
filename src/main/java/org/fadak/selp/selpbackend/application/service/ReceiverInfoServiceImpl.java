package org.fadak.selp.selpbackend.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.dto.request.ReceiverModifyRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.ReceiverRegisterRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.ReceiverInfoListResponseDto;
import org.fadak.selp.selpbackend.domain.entity.Preference;
import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;
import org.fadak.selp.selpbackend.domain.repository.PreferenceRepository;
import org.fadak.selp.selpbackend.domain.repository.ReceiverInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiverInfoServiceImpl implements ReceiverInfoService {

    private final PreferenceRepository preferenceRepository;
    private final ReceiverInfoRepository repository;
    private final MemberService memberService;

    @Override
    public List<ReceiverInfoListResponseDto> getReceiverInfoList(long memberId) {

        List<ReceiverInfo> receiverInfoList = repository.findAllByMemberId(memberId);

        return receiverInfoList.stream()
            .map(receiverInfo -> {
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

        request.getPreferenceIds()
            .forEach(
                preferenceId -> {
                    Preference pref = preferenceRepository.findById(preferenceId)
                        .orElseThrow(IllegalArgumentException::new);
                    receiverInfo.addPreference(pref);
                }
            );

        repository.save(receiverInfo);
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

        request.getPreferenceIds()
            .forEach(
                preferenceId -> {
                    Preference pref = preferenceRepository.findById(preferenceId)
                        .orElseThrow(IllegalArgumentException::new);
                    receiverInfo.addPreference(pref);
                }
            );

        repository.save(receiverInfo);
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
