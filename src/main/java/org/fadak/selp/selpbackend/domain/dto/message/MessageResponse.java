package org.fadak.selp.selpbackend.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MessageResponse {
    private List<String> messages;
}