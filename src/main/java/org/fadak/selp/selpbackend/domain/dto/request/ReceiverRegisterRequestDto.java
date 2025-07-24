package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReceiverRegisterRequestDto {

    private String nickname;

    private String gender;

    private String relationship;

    private int age;

    private String preferences;

    private String detail;
}
