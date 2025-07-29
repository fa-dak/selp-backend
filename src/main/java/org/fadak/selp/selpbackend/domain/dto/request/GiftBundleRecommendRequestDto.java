package org.fadak.selp.selpbackend.domain.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.fadak.selp.selpbackend.domain.constant.Gender;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class GiftBundleRecommendRequestDto {

    private int ageRange;
    private String anniversaryType;
    private List<String> categories;
    private String relation;
    private Gender gender;
    private int budget;
    private String userMessage;
}
