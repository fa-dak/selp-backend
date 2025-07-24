package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReceiverInfoListResponseDto {

    private long receiverInfoId;
    private String receiverNickname;
    private int receiverAge;
    private String receiverGender;
    private String relationship;
    private String receiverPreferences;
    private String receiverDetail;
}
