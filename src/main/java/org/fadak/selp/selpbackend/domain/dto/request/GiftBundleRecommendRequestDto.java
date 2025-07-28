package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class GiftBundleRecommendRequestDto {
    private String ageRange;
    private String anniversaryType;
    private List<String> categories;
    private String relation;
    private String gender;
    private int budget;
    private String userMessage;
}
