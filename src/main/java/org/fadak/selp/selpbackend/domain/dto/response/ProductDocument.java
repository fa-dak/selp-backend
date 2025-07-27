package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDocument {
    private String productId;
    private String name;
    private int price;
    private String imagePath;
    private String detailPath;
    private String category;
    private List<Double> embedding; // Elasticsearch에 저장된 벡터 필드
}