package org.fadak.selp.selpbackend.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {

    BIRTHDAY("생일"),
    CHILDBIRTH("출산"),
    ENTRANCE("입학"),
    RETIREMENT("퇴사"),
    ANNIVERSARY("기념일"),
    WEDDING_ANNIVERSARY("결혼 기념일"),
    GRADUATION("졸업"),
    HOLIDAY("명절"),
    ETC("기타");

    private final String value;
}
