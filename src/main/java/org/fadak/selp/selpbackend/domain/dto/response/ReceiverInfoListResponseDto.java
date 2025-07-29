package org.fadak.selp.selpbackend.domain.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.fadak.selp.selpbackend.domain.constant.Gender;

@Getter
@Builder
public class ReceiverInfoListResponseDto {

    private long receiverInfoId;

    private String receiverNickname;

    private int receiverAge;

    private Gender receiverGender;

    private String relationship;

    private List<String> receiverPreferences;

    private String receiverDetail;

}
