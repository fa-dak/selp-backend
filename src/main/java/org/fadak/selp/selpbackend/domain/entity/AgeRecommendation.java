package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Setter
@Table(name = "AGE_RECOMMENDATION")
public class AgeRecommendation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AGE_RECOMMEND_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "DETAIL_PATH", length = 1024)
    private String detailPath;

    @Column(name = "IMAGE_PATH", length = 1024)
    private String imagePath;

    @Column(name = "AGE_GROUP")
    private String ageGroup;
}
