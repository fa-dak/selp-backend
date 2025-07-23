package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRODUCT_IMG")
public class ProductImg extends BaseEntity {

    @Id
    @Column(name = "PRODUCT_IMG_ID")
    private String id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "IMG_URL")
    private String img_url;

    @Column(name = "Field")
    private String field;

    @Column(name = "Field2")
    private String field2;

    @Column(name = "Field3")
    private String field3;

    @Column(name = "Field4")
    private String field4;
}