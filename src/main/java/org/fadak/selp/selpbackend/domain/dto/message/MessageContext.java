package org.fadak.selp.selpbackend.domain.dto.message;

import lombok.Builder;
import lombok.Getter;
import org.fadak.selp.selpbackend.domain.constant.Gender;

@Getter
@Builder
public class MessageContext {

    private String style;
    private Gender gender;
    private int age;
    private String relationship;
    private String occasion;
}
