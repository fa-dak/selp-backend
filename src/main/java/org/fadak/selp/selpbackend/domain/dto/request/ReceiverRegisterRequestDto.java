package org.fadak.selp.selpbackend.domain.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.fadak.selp.selpbackend.domain.constant.Gender;

@Getter
@Setter // JSON 매핑
@AllArgsConstructor
public class ReceiverRegisterRequestDto {

    //    @NotBlank(message = "별명은 필수입니다.")
    private String nickname;

//    @NotNull(message = "성별은 필수입니다.")
    private Gender gender;

//    @NotBlank(message = "관계는 필수입니다.")
    private String relationship;

    private int age;

//    @NotNull(message = "취향 ID 목록은 필수입니다.")
//    @Size(min = 1, message = "최소 하나 이상의 취향 ID가 필요합니다.")
    private List<Long> preferenceIds;

//    @Size(max = 255, message = "세부 사항은 255자 이내여야 합니다.")
    private String detail;
}
