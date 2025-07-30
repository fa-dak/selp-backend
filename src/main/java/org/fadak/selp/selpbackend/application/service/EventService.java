package org.fadak.selp.selpbackend.application.service;

import java.util.List;
import org.fadak.selp.selpbackend.domain.dto.request.EventListSearchRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.EventModifyRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.EventRegisterRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.EventDetailResponseDto;
import org.fadak.selp.selpbackend.domain.dto.response.EventListResponseDto;

public interface EventService {

    List<EventListResponseDto> getEventList(EventListSearchRequestDto request, long loginMemberId);

    void delete(long eventId, long loginMemberId);

    void registerEvent(EventRegisterRequestDto request, long loginMemberId);

    void modifyEvent(EventModifyRequestDto request, long eventId, long loginMemberId);

    EventDetailResponseDto getEventDetail(long eventId, long loginMemberId);
}
