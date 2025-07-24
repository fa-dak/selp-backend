/**
 * @담당자: 정재영
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
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "EVENT")
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_INFO_ID")
    private ReceiverInfo receiverInfo;

    @Column(name = "EVENT_TYPE")// TODO: TYPE 로 수정하기
    private String eventType; // TODO: type 로 수정하기

    @Column(name = "EVENT_DATE") // TODO: DATE 로 수정하기
    private LocalDate eventDate; // TODO: Date 로 수정하기

    @Column(name = "NOTIFICATION_DAYS_BEFORE")
    private Integer notificationDaysBefore;

    @Builder
    public Event(
        ReceiverInfo receiverInfo,
        String eventType,
        LocalDate eventDate,
        Integer notificationDaysBefore
    ) {

        this.receiverInfo = receiverInfo;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.notificationDaysBefore = notificationDaysBefore;
    }
}