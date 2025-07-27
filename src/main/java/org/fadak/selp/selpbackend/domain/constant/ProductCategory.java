package org.fadak.selp.selpbackend.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductCategory {
    BEAUTY("beauty"), FOOD("food"), LIVING("living"), CHILDREN("children"), SPORTS("sports"), FASHION("fashion"), FLOWER("flower"), DESERT("desert");

    private final String description;
}
