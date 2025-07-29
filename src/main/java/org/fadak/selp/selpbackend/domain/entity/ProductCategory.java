package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT_CATEGORY")
public class ProductCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_CATEGORY_ID")
    private Long id;

    @Column(name = "CATEGORY_NAME")
    private String name;
}

//BEAUTY("beauty"),
//FOOD("food"),
//LIVING("living"),
//CHILDREN("children"),
//SPORTS("sports"),
//FASHION("fashion"),
//FLOWER("flower"),
//DESERT("desert");