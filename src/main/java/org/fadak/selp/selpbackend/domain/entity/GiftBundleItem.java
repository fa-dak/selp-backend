package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "GIFT_BUNDLE_ITEM")
public class GiftBundleItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GIFT_BUNDLE_ITEM_ID")
    private Long id;

    @Column(name = "GIFT_BUNDLE_ID")
    private Long giftBundleId;

    @Column(name = "PRODUCT_ID")
    private Long productId;
}