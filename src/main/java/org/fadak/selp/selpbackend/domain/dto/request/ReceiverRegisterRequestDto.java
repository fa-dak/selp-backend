package org.fadak.selp.selpbackend.domain.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // JSON 매핑
@AllArgsConstructor
public class ReceiverRegisterRequestDto {

    @NotBlank(message = "별명은 필수입니다.")
    private String nickname;

    @NotBlank(message = "성별은 필수입니다.")
    @Pattern(regexp = "^(남자|여자|모두)$", message = "성별은 '남자', '여자', '모두' 중 하나여야 합니다.")
    private String gender;

    @NotBlank(message = "관계는 필수입니다.")
    private String relationship;

    @Min(value = 0, message = "나이는 0 이상이어야 합니다.")
    @Max(value = 150, message = "나이는 150 이하이어야 합니다.")
    private int age;

    @NotNull(message = "취향 ID 목록은 필수입니다.")
    @Size(min = 1, message = "최소 하나 이상의 취향 ID가 필요합니다.")
    private List<@NotNull(message = "취향 ID는 null일 수 없습니다.") Long> preferenceIds;

    @Size(max = 255, message = "세부 사항은 255자 이내여야 합니다.")
    private String detail;
}
