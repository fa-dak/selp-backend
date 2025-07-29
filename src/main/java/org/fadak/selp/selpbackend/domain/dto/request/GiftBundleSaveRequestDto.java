package org.fadak.selp.selpbackend.domain.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.fadak.selp.selpbackend.domain.constant.EventType;
import org.fadak.selp.selpbackend.domain.constant.Gender;
import org.fadak.selp.selpbackend.domain.entity.Event;
import org.fadak.selp.selpbackend.domain.entity.Member;
import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;

@Getter
@AllArgsConstructor
public class GiftBundleSaveRequestDto {

    private List<Long> giftIds;
    private int ageRange; // 상대방 나이
    private EventType anniversaryType; // 기념일 종류
    private List<String> categories; // 상대방 선호 카테고리
    private String relation; // 관계
    private Gender gender; // 성별
    private String detail; // 상대방 특징


    public ReceiverInfo toReceiverInfo(Member member) {

        return ReceiverInfo.builder()
            .age(ageRange)
            .relationship(relation)
            .gender(gender)
            .member(member)
            .detail(detail)
            .build();
    }

    public Event toEvent(ReceiverInfo receiverInfo) {

        return Event.builder()
            .eventDate(LocalDate.now())
            .eventType(anniversaryType)
            .receiverInfo(receiverInfo)
            .build();
    }
}
