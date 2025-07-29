package org.fadak.selp.selpbackend.application.service;

import java.util.List;
import org.fadak.selp.selpbackend.domain.dto.request.ReceiverModifyRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.ReceiverRegisterRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.ReceiverInfoListResponseDto;
import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;

public interface ReceiverInfoService {

    List<ReceiverInfoListResponseDto> getReceiverInfoList(long memberId);

    ReceiverInfoListResponseDto getReceiverInfoDetail(long receiverInfoId, long memberId);

    void delete(long receiverInfoId, long loginMemberId);

    void registerReceiverInfo(ReceiverRegisterRequestDto request, long loginMemberId);

    void modifyReceiverInfo(ReceiverModifyRequestDto request, Long receiverInfoId,
        long loginMemberId);

    ReceiverInfo getReceiverInfo(long receiverId);
}
