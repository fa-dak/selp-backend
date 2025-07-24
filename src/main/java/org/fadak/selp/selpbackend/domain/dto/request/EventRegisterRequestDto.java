package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // JSON 매핑
@AllArgsConstructor
public class EventRegisterRequestDto {

    private String eventDate; // 형식: YYYY-MM-DD

    private String eventName;

    private String eventType;

    private long receiverId;

    private Integer notificationDaysBefore; // null 가능
}
