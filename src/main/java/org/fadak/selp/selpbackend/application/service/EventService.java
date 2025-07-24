package org.fadak.selp.selpbackend.application.service;

import java.util.List;
import org.fadak.selp.selpbackend.domain.dto.request.EventListSearchRequestDto;
import org.fadak.selp.selpbackend.domain.entity.Event;

public interface EventService {

    List<Event> getEventList(EventListSearchRequestDto request, long loginMemberId);

    void delete(long eventId, long loginMemberId);
}
