package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NotificationRequestDto {
    private Long memberId;
    private Long eventId;
    private String title;
    private String content;
    private LocalDate sendDate;
}