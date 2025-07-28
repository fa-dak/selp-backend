package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Preference extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PREFERENCE_ID")
    private Long id;

    @JoinColumn(name = "PRODUCT_CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_INFO_ID")
    private ReceiverInfo receiverInfo;


    @Builder
    public Preference(ProductCategory category, ReceiverInfo receiverInfo) {
        this.category = category;
        this.receiverInfo = receiverInfo;
    }

}
