package org.fadak.selp.selpbackend.domain.dto.message;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MessageRequest {
    private Long bundleId;
    private String tone;
}