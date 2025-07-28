package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.fadak.selp.selpbackend.domain.constant.ProductCategory;

@Getter
@Entity
public class Preference extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PREFERENCE_ID")
    private Long id;

    @Column(name = "CATEGORY")
    private ProductCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_INFO_ID")
    private ReceiverInfo receiverInfo;
}
