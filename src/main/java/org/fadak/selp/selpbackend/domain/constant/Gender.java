package org.fadak.selp.selpbackend.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("남성"),
    FEMALE("여성"),
    NONE("상관없음");

    private final String value;
}
