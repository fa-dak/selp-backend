/**
 * @담당자: 한상준
 */

package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

import lombok.*;
import org.fadak.selp.selpbackend.domain.dto.request.NotificationRequestDto;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NOTIFICATION")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTIFICATION_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "IS_SENT")
    private Boolean isSent = false;

    @Column(name = "IS_READ")
    private Boolean isRead = false;

    @Column(name = "SEND_DATE")
    private LocalDate sendDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID")
    private Event event;

    @Builder
    public Notification(Member member, String title, String content, LocalDate sendDate, Event event) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.isSent = false;
        this.isRead = false;
        this.sendDate = sendDate;
        this.event = event;
    }

    public static Notification of(Member member, Event event, NotificationRequestDto dto) {
        return Notification.builder()
            .member(member)
            .event(event)
            .title(dto.getTitle())
            .content(dto.getContent())
            .sendDate(dto.getSendDate())
            .build();
    }
}
