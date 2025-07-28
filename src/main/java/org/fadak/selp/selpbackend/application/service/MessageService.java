package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.dto.message.MessageRequest;
import org.fadak.selp.selpbackend.domain.dto.message.MessageResponse;

public interface MessageService {
    MessageResponse recommendMessage(Long bundleId, String tone);
}