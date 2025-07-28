package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.fadak.selp.selpbackend.domain.constant.ProductCategory;
import org.fadak.selp.selpbackend.domain.entity.Event;
import org.fadak.selp.selpbackend.domain.entity.GiftBundle;
import org.fadak.selp.selpbackend.domain.entity.Product;
import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
public class GiftBundleResponseDto {
    private final Long giftBundleId;
    private final String createdDate;

    private final Long eventId;
    private final String eventType;
    private final LocalDate eventDate;
    private final String eventName;

    private final Long receiverInfoId;
    private final String receiverNickname;
    private final String relationship;

    private final List<ProductDto> products;

    public static GiftBundleResponseDto from(GiftBundle giftBundle, List<ProductDto> productDtos) {
        Event event = giftBundle.getEvent();
        ReceiverInfo receiverInfo = event.getReceiverInfo();

        String formattedCreatedDate = giftBundle.getCreatedDate()
                .format(DateTimeFormatter.ofPattern("yy.MM.dd"));

        return GiftBundleResponseDto.builder()
                .giftBundleId(giftBundle.getId())
                .createdDate(formattedCreatedDate)
                .eventId(event.getId())
                .eventType(event.getEventType())
                .eventDate(event.getEventDate())
                .eventName(event.getEventName())
                .receiverInfoId(receiverInfo.getId())
                .receiverNickname(receiverInfo.getNickname())
                .relationship(receiverInfo.getRelationship())
                .products(productDtos)
                .build();
    }

    @Getter
    @Builder
    public static class ProductDto {
        private final Long productId;
        private final ProductCategory category;
        private final String name;
        private final Long price;
        private final String imagePath;
        private final String detailPath;

        public static ProductDto from(Product product) {
            return ProductDto.builder()
                    .productId(product.getId())
                    .category(product.getCategory())
                    .name(product.getName())
                    .price(product.getPrice())
                    .imagePath(product.getImagePath())
                    .detailPath(product.getDetailPath())
                    .build();
        }
    }
}
