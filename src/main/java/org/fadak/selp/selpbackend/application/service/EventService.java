package org.fadak.selp.selpbackend.application.service;

import java.util.List;
import org.fadak.selp.selpbackend.domain.dto.request.EventListRequestDto;
import org.fadak.selp.selpbackend.domain.entity.Event;

public interface EventService {

    List<Event> getEventList(EventListRequestDto request, long loginMemberId);
}
