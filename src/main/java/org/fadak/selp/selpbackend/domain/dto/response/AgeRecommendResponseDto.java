package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AgeRecommendResponseDto {
    private Long id;

    private String name;

    private Long price;

    private String imageUrl;

    private String detailPath;

    private String ageGroup;
}
