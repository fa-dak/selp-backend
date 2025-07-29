package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "PAYMENT")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GIFT_BUNDLE_ID")
    private GiftBundle giftBundle;

    @Column(name = "IMP_UID")
    private String impUid;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "STATUS")
    private PayStatus status;

    @Builder
    public Payment(GiftBundle giftBundle, String impUid, int amount, PayStatus status) {

        this.giftBundle = giftBundle;
        this.impUid = impUid;
        this.amount = amount;
        this.status = status;
    }
}
