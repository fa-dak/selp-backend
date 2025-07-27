package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
}
