package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fadak.selp.selpbackend.domain.entity.Event;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventPreviewDto {

    private Long id;
    private String eventName;
    private long dDay;

    public static EventPreviewDto from(Event event) {
        long dDay = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
        return EventPreviewDto.builder()
                .id(event.getId())
                .eventName(event.getEventName())
                .dDay(dDay)
                .build();
    }
}
