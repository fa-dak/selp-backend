package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.fadak.selp.selpbackend.domain.entity.Product;

@Getter
@AllArgsConstructor
@Builder
public class GiftBundleItemResponseDto {
    private Long id;
    private Long price;
    private String name;
    private String imagePath;
    private String detailPath;
    private String category;

    public static GiftBundleItemResponseDto from(Product product) {
        return GiftBundleItemResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory().getName())
                .imagePath(product.getImagePath())
                .detailPath(product.getDetailPath())
                .build();
    }
}
