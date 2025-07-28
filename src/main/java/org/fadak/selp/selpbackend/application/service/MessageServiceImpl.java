package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.application.util.GptUtil;
import org.fadak.selp.selpbackend.domain.dto.message.MessageContext;
import org.fadak.selp.selpbackend.domain.dto.message.MessageResponse;
import org.fadak.selp.selpbackend.domain.entity.Event;
import org.fadak.selp.selpbackend.domain.entity.GiftBundle;
import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;
import org.fadak.selp.selpbackend.domain.repository.EventRepository;
import org.fadak.selp.selpbackend.domain.repository.GiftBundleRepository;
import org.fadak.selp.selpbackend.domain.repository.ReceiverInfoRepository;
import org.fadak.selp.selpbackend.exception.MessageException;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ReceiverInfoRepository receiverRepo;
    private final GiftBundleRepository giftBundleRepo;
    private final EventRepository eventRepo;
    private final ChatModel chatModel;

    @Override
    public MessageResponse recommendMessage(Long bundleId, String tone) throws MessageException {
        // 1. giftBundle → event
        GiftBundle giftBundle = giftBundleRepo.findById(bundleId)
                .orElseThrow(() -> new MessageException("선물꾸러미 없음"));
        Event event = eventRepo.findById(giftBundle.getEvent().getId())
                .orElseThrow(() -> new MessageException("기념일 없음"));

        // 2. event → receiver
        ReceiverInfo receiver = receiverRepo.findById(event.getReceiverInfo().getId())
                .orElseThrow(() -> new MessageException("받는 사람 정보 없음"));

        // 3. context 구성
        MessageContext context = MessageContext.builder()
                .style(tone)
                .gender(receiver.getGender())
                .age(receiver.getAge())
                .relationship(receiver.getRelationship())
                .occasion(event.getEventType())
                .build();

        String promptText = GptUtil.buildMessagePrompt(context);
        log.info("prompt:: " + promptText);

        String generation = GptUtil.callGpt(chatModel, promptText);
        log.info("response:: {}", generation);

        List<String> messages = Arrays.stream(generation.split("\n"))
                .map(line -> line.replaceAll("^\\d+\\.\\s*", ""))
                .filter(StringUtils::hasText)
                .collect(Collectors.toList());

        return new MessageResponse(messages);
    }

}