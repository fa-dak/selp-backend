package org.fadak.selp.selpbackend.application.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.dto.request.EventListRequestDto;
import org.fadak.selp.selpbackend.domain.entity.Event;
import org.fadak.selp.selpbackend.domain.repository.EventRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    @Override
    public List<Event> getEventList(EventListRequestDto request, long loginMemberId) {

        LocalDate localDate = LocalDate.parse(request.getDate());

        return repository.findByEventDateAndReceiverInfo_Member_Id(localDate, loginMemberId);
    }
}
