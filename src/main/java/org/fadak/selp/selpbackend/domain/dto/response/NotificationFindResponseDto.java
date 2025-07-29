package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.fadak.selp.selpbackend.domain.entity.Notification;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class NotificationFindResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private Boolean isRead;

    public static NotificationFindResponseDto from(Notification notification) {
        return NotificationFindResponseDto
                .builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .createdDate(notification.getCreatedDate())
                .isRead(notification.getIsRead())
                .build();
    }
}
