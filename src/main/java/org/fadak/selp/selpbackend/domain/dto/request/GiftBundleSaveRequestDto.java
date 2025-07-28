package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.fadak.selp.selpbackend.domain.entity.Member;
import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;

import java.util.List;

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

    public ReceiverInfo toReceiverInfo(Member member) {
        return ReceiverInfo.builder()
                .
                .build();
    }
}
