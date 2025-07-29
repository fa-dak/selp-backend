package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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