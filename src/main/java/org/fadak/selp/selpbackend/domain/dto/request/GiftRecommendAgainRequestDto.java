package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GiftRecommendAgainRequestDto {
    private Long productId;
    private String ageRange;
    private String anniversaryType;
    private String category;
    private String relation;
    private String gender;
    private int price;
    private String userMessage;

    public GiftBundleRecommendRequestDto toGiftBundleRecommendRequestDto() {
        return GiftBundleRecommendRequestDto.builder()
                .ageRange(ageRange)
                .anniversaryType(anniversaryType)
                .budget(price)
                .categories(null)
                .gender(gender)
                .relation(relation)
                .userMessage(userMessage)
                .build();
    }
}
