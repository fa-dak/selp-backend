package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.domain.dto.response.EventPreviewDto;
import org.fadak.selp.selpbackend.domain.dto.response.HomeResponseDto;
import org.fadak.selp.selpbackend.domain.dto.response.ProductPreviewDto;
import org.fadak.selp.selpbackend.domain.entity.Event;
import org.fadak.selp.selpbackend.domain.entity.GiftBundle;
import org.fadak.selp.selpbackend.domain.entity.Product;
import org.fadak.selp.selpbackend.domain.repository.EventRepository;
import org.fadak.selp.selpbackend.domain.repository.GiftBundleItemRepository;
import org.fadak.selp.selpbackend.domain.repository.GiftBundleRepository;
import org.fadak.selp.selpbackend.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService{

    private final EventRepository eventRepository;
    private final ProductRepository productRepository;
    private final GiftBundleRepository giftBundleRepository;
    private final GiftBundleItemRepository giftBundleItemRepository;

    @Override
    public HomeResponseDto getHome(Long memberId) {
        // 1. 다가오는 이벤트
        List<Event> events = eventRepository.findTop5UpcomingEvents(memberId, LocalDate.now());
        List<EventPreviewDto> eventDtos = events.stream()
                .map(EventPreviewDto::from)
                .toList();

        // 2. 랜덤 상품
        List<Product> products = productRepository.findRandomProducts(8);
        List<ProductPreviewDto> productDtos = products.stream()
                .map(ProductPreviewDto::from)
                .toList();

        // 3. 최근 선물꾸러미
        GiftBundle recentBundle = giftBundleRepository.findTopByMemberIdOrderByCreatedDateDesc(memberId)
                .orElse(null);

        List<ProductPreviewDto> recentBundleProductDtos = new ArrayList<>();
        if (recentBundle != null) {
            List<Product> bundleProducts = giftBundleItemRepository.findAllProductsByBundleId(recentBundle.getId());
            recentBundleProductDtos = bundleProducts.stream()
                    .map(ProductPreviewDto::from)
                    .toList();
        }

        return HomeResponseDto.of(eventDtos, productDtos, recentBundleProductDtos);
    }
}
