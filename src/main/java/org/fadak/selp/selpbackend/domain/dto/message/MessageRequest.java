package org.fadak.selp.selpbackend.domain.dto.message;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MessageRequest {
    private Long receiverInfoId;
    private List<Long> productIdList;
    private Long eventId;
    private String style;           // 예: 감동형, 유머형 등
    private String additionalNote;  // 사용자가 입력한 부가 설명
}