package org.fadak.selp.selpbackend.application.service;

import java.util.List;
import org.fadak.selp.selpbackend.domain.dto.response.ReceiverInfoListResponseDto;

public interface ReceiverInfoService {

    List<ReceiverInfoListResponseDto> getReceiverInfoList(long memberId);

    void delete(long receiverInfoId, long loginMemberId);
}
