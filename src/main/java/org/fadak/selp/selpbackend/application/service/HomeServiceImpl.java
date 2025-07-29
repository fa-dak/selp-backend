package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.domain.dto.response.*;
import org.fadak.selp.selpbackend.domain.entity.Event;
import org.fadak.selp.selpbackend.domain.entity.GiftBundle;
import org.fadak.selp.selpbackend.domain.entity.Product;
import org.fadak.selp.selpbackend.domain.repository.EventRepository;
import org.fadak.selp.selpbackend.domain.repository.GiftBundleRepository;
import org.fadak.selp.selpbackend.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService{

    private final EventRepository eventRepository;
    private final ProductRepository productRepository;
    private final GiftBundleRepository giftBundleRepository;

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
        GiftBundle giftBundle = giftBundleRepository.findTopByMemberIdOrderByCreatedDateDesc(memberId)
                .orElse(null);

        GiftBundleResponseDto giftBundleResponseDto = null;
        if (giftBundle != null) {
            giftBundleResponseDto = convertToDto(giftBundle);
        }

        return HomeResponseDto.of(eventDtos, productDtos, giftBundleResponseDto);
    }

    private GiftBundleResponseDto convertToDto(GiftBundle giftBundle) {
        // ProductDto 리스트 생성
        List<GiftBundleResponseDto.ProductDto> productDtos = giftBundle.getGiftBundleItems().stream()
                .map(item -> GiftBundleResponseDto.ProductDto.from(item.getProduct()))
                .collect(Collectors.toList());

        // 최종 DTO 조립
        return GiftBundleResponseDto.from(giftBundle, productDtos);
    }
}
