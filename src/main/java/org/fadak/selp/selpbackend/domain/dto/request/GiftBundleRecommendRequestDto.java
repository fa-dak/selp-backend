package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.fadak.selp.selpbackend.domain.constant.EventType;
import org.fadak.selp.selpbackend.domain.constant.Gender;

import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class GiftBundleRecommendRequestDto {

    private int ageRange;
    private EventType anniversaryType;
    private List<String> categories;
    private String relation;
    private Gender gender;
    private int budget;
    private String userMessage;
}
