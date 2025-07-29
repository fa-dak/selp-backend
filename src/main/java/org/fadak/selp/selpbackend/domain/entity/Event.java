/**
 * @담당자: 정재영
 */

package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "EVENT_NAME")// TODO: NAME 로 수정하기
    private String eventName; // TODO: name 로 수정하기

    @Column(name = "EVENT_DATE") // TODO: DATE 로 수정하기
    private LocalDate eventDate; // TODO: Date 로 수정하기

    @Column(name = "NOTIFICATION_DAYS_BEFORE")
    private Integer notificationDaysBefore;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<GiftBundle> giftBundles;

    @Builder
    public Event(
        ReceiverInfo receiverInfo,
        String eventType,
        String eventName,
        LocalDate eventDate,
        Integer notificationDaysBefore
    ) {

        this.receiverInfo = receiverInfo;
        this.eventType = eventType;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.notificationDaysBefore = notificationDaysBefore;
    }

}