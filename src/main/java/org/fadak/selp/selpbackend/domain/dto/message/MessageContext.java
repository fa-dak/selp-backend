package org.fadak.selp.selpbackend.domain.dto.message;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageContext {
    private String style;
    private String gender;
    private int age;
    private String relationship;
    private String occasion;
}
