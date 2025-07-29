/**
 * @담당자: 원승현
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

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @JoinColumn(name = "PRODUCT_CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
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