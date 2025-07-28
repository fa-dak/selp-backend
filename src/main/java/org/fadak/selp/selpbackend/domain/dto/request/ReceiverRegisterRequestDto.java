package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter // JSON 매핑
@AllArgsConstructor
public class ReceiverRegisterRequestDto {

    private String nickname;

    private String gender;

    private String relationship;

    private int age;

    private List<String> preferences;

    private String detail;
}
