package org.fadak.selp.selpbackend.application.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.EventService;
import org.fadak.selp.selpbackend.domain.dto.request.EventListRequestDto;
import org.fadak.selp.selpbackend.domain.entity.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEventList(
        EventListRequestDto request
    ) {

        long loginMemberId = 1L;
        List<Event> eventList = eventService.getEventList(request, loginMemberId);
        return ResponseEntity.ok(eventList);
    }
}
