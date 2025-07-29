package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fadak.selp.selpbackend.domain.entity.Product;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPreviewDto {

    private Long id;
    private String name;
    private Long price;
    private String imagePath;
    private String detailPath;

    public static ProductPreviewDto from(Product product) {
        return ProductPreviewDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .imagePath(product.getImagePath())
                .detailPath(product.getDetailPath())
                .build();
    }
}