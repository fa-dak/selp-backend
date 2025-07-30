package org.fadak.selp.selpbackend.domain.entity; /**
 * @담당자: 원승현, 이지연
 */

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.fadak.selp.selpbackend.domain.constant.PayStatus;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GIFT_BUNDLE")
public class GiftBundle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GIFT_BUNDLE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID")
    private Event event;


    @Column(name = "CURRENT_PAY_STATUS")
    @Enumerated(EnumType.STRING)
    private PayStatus currentPayStatus = PayStatus.NOT_STARTED;

    @OneToMany(mappedBy = "giftBundle", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<GiftBundleItem> giftBundleItems = new ArrayList<>();

    @Builder
    public GiftBundle(Member member, Event event, PayStatus currentPayStatus) {

        this.member = member;
        this.event = event;
        this.currentPayStatus = currentPayStatus;
    }
}