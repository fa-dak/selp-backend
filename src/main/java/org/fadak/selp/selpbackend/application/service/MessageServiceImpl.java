package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.facade.MessageFacade;
import org.fadak.selp.selpbackend.domain.dto.message.MessageRequest;
import org.fadak.selp.selpbackend.domain.dto.message.MessageResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageFacade facade;

    @Override
    public MessageResponse recommendMessage(MessageRequest request) {
        return facade.handleRecommendation(request);
    }
}