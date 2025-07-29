package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.fadak.selp.selpbackend.domain.constant.EventType;
import org.fadak.selp.selpbackend.domain.constant.Gender;

@Getter
@Builder
@AllArgsConstructor
public class GiftRecommendAgainRequestDto {

    private Long productId;
    private int ageRange;
    private EventType anniversaryType;
    private String category;
    private String relation;
    private Gender gender;
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
