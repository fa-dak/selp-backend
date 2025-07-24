package org.fadak.selp.selpbackend.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.dto.response.ReceiverInfoListResponseDto;
import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;
import org.fadak.selp.selpbackend.domain.repository.ReceiverInfoRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiverInfoServiceImpl implements ReceiverInfoService {

    private final ReceiverInfoRepository repository;

    @Override
    public List<ReceiverInfoListResponseDto> getReceiverInfoList(long memberId) {

        List<ReceiverInfo> receiverInfoList = repository.findAllByMemberId(memberId);

        return receiverInfoList.stream()
            .map(receiverInfo ->
                ReceiverInfoListResponseDto.builder()
                    .receiverInfoId(receiverInfo.getId())
                    .receiverDetail(receiverInfo.getDetail())
                    .receiverGender(receiverInfo.getGender())
                    .receiverNickname(receiverInfo.getNickname())
                    .receiverPreferences(receiverInfo.getPreferences())
                    .receiverAge(receiverInfo.getAge())
                    .relationship(receiverInfo.getRelationship())
                    .build()
            ).toList();
    }

    @Override
    public void delete(long receiverInfoId, long loginMemberId) {

        repository.deleteByIdAndMember_Id(receiverInfoId, loginMemberId);
    }
}
