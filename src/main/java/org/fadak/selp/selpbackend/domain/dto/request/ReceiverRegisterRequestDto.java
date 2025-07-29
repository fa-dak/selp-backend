package org.fadak.selp.selpbackend.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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


    @NotBlank(message = "성별은 필수입니다.")
    @Pattern(regexp = "^(MALE|FEMALE|NONE)$", message = "성별은 'MALE', 'FEMALE', 'NONE' 중 하나여야 합니다.")
    private Gender gender;

    @NotBlank(message = "관계는 필수입니다.")
    private String relationship;

    private int age;

    @NotNull(message = "취향 ID 목록은 필수입니다.")
    @Size(min = 1, message = "최소 하나 이상의 취향 ID가 필요합니다.")
    private List<@NotNull(message = "취향 ID는 null일 수 없습니다.") Long> preferenceIds;

    @Size(max = 255, message = "세부 사항은 255자 이내여야 합니다.")
    private String detail;
}
