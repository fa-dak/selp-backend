package org.fadak.selp.selpbackend.application.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.application.service.EventService;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.fadak.selp.selpbackend.domain.dto.request.EventListSearchRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.EventModifyRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.EventRegisterRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.EventListResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventListResponseDto>> getAllEventList(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @ModelAttribute EventListSearchRequestDto request
    ) {

        log.info("request: {}-{}", request.getYear(), request.getMonth());
        long loginMemberId = userPrincipal.getId();
        List<EventListResponseDto> eventList = eventService.getEventList(request, loginMemberId);
        return ResponseEntity.ok(eventList);
    }

    @DeleteMapping("/{event-id}")
    public ResponseEntity<Void> deleteEvent(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable(name = "event-id") long eventId
    ) {

        long loginMemberId = userPrincipal.getId();
        eventService.delete(eventId, loginMemberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    ResponseEntity<Void> registerEvent(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @RequestBody EventRegisterRequestDto request
    ) {

        long loginMemberId = userPrincipal.getId();
        eventService.registerEvent(request, loginMemberId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{event-id}")
    ResponseEntity<Void> modifyEvent(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable(name = "event-id") long eventId,
        @RequestBody EventModifyRequestDto request
    ) {

        long loginMemberId = userPrincipal.getId();
        eventService.modifyEvent(request, eventId, loginMemberId);
        return ResponseEntity.ok().build();
    }
}
