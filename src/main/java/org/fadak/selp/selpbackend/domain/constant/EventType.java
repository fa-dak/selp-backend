package org.fadak.selp.selpbackend.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {

    BIRTHDAY("생일"),
    ANNIVERSARY("기념일"),
    WEDDING_ANNIVERSARY("결혼 기념일"),
    HOLIDAY("휴일"),
    ETC("기타");

    private final String value;
}
