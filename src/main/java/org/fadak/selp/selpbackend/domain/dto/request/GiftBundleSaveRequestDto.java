package org.fadak.selp.selpbackend.domain.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GiftBundleSaveRequestDto {

    private List<Long> giftIds;
    private String ageRange; // 상대방 나이
    private String anniversaryType; // 기념일 종류
    private List<String> categories; // 상대방 선호 카테고리
    private String relation; // 관계
    private String gender; // 성별
    private String userMessage; // 상대방 특징


}
