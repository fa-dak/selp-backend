/**
 * @담당자: 원승현
 */

package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.fadak.selp.selpbackend.domain.constant.ProductCategory;

@Getter
@Entity
@Table(name = "PRODUCT")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private ProductCategory category;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMAGE_PATH", length = 1024)
    private String imagePath;

    @Column(name = "DETAIL_PATH", length = 1024)
    private String detailPath;
}