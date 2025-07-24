package org.fadak.selp.selpbackend.application.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.application.util.GptUtil;
import org.fadak.selp.selpbackend.domain.dto.message.MessageContext;
import org.fadak.selp.selpbackend.domain.dto.message.MessageRequest;
import org.fadak.selp.selpbackend.domain.dto.message.MessageResponse;
import org.fadak.selp.selpbackend.domain.entity.Event;
import org.fadak.selp.selpbackend.domain.entity.Product;
import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;
import org.fadak.selp.selpbackend.domain.repository.EventRepository;
import org.fadak.selp.selpbackend.domain.repository.ProductRepository;
import org.fadak.selp.selpbackend.domain.repository.ReceiverInfoRepository;
import org.fadak.selp.selpbackend.exception.MessageException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageFacade {

    private final ReceiverInfoRepository receiverRepo;
    private final ProductRepository productRepo;
    private final EventRepository eventRepo;
    private final GptUtil gptUtil;

    public MessageResponse handleRecommendation(MessageRequest request) {

        ReceiverInfo receiver = receiverRepo.findById(request.getReceiverInfoId())
                .orElseThrow(() -> new MessageException("받는 사람 없음"));

        Event event = eventRepo.findById(request.getEventId())
                .orElseThrow(() -> new MessageException("기념일 없음"));

        List<Product> products = productRepo.findAllById(request.getProductIdList());
        String giftNames = products.stream()
                .map(Product::getName)
                .collect(Collectors.joining(", "));

        MessageContext context = MessageContext.builder()
                .style(request.getStyle())
                .gender(receiver.getGender())
                .age(receiver.getAge())
                .relationship(receiver.getRelationship())
                .giftName(giftNames)
                .occasion(event.getEventType())
                .additionalNote(request.getAdditionalNote())
                .build();

        String prompt = gptUtil.buildMessagePrompt(context);
        log.info("prompt:: " + prompt);
        String rawResponse = gptUtil.callGpt(prompt);

        List<String> messages = Arrays.stream(rawResponse.split("\n"))
                .map(line -> line.replaceAll("^\\d+\\.\\s*", ""))
                .filter(StringUtils::hasText)
                .collect(Collectors.toList());

        return new MessageResponse(messages);
    }
}
