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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fadak.selp.selpbackend.domain.dto.request.NotificationRequestDto;

import java.time.LocalDate;
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NOTIFICATION")

public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTIFICATION_ID")
    private Long id;

    @Column(name = "EVENT_ID")
    private Long eventId;

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

    public static Notification of(Member member, NotificationRequestDto dto) {
        return new Notification(
                null,
                dto.getEventId(),
                member,
                dto.getTitle(),
                dto.getContent(),
                false,
                false,
                dto.getSendDate()
        );
    }

}
