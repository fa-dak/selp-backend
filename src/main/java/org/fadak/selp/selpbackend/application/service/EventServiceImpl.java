package org.fadak.selp.selpbackend.application.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.dto.request.EventListSearchRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.EventModifyRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.EventRegisterRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.EventListResponseDto;
import org.fadak.selp.selpbackend.domain.entity.Event;
import org.fadak.selp.selpbackend.domain.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository repository;
    private final ReceiverInfoService receiverInfoService;

    @Override
    public List<EventListResponseDto> getEventList(EventListSearchRequestDto request,
        long loginMemberId) {

        YearMonth yearMonth = YearMonth.of(request.getYear(), request.getMonth());
        LocalDate startDate = yearMonth.atDay(1); // 2025-07-01
        LocalDate endDate = yearMonth.atEndOfMonth(); // 2025-07-31

        List<Event> eventList = repository.findByEventDateBetweenAndReceiverInfo_Member_Id(
            startDate,
            endDate,
            loginMemberId);

        List<EventListResponseDto> responseList = eventList.stream()
            .map(event -> EventListResponseDto.builder()
                .eventId(event.getId())
                .eventName(event.getEventName())
                .eventType(event.getEventType())
                .receiverNickname(event.getReceiverInfo().getNickname())
                .notificationDaysBefore(event.getNotificationDaysBefore())
                .build())
            .toList();

        return responseList;
    }

    @Override
    @Transactional
    public void delete(long eventId, long loginMemberId) {
        
        repository.deleteByIdAndReceiverInfo_Member_Id(eventId, loginMemberId);
    }

    @Override
    @Transactional
    public void registerEvent(EventRegisterRequestDto request, long loginMemberId) {

        LocalDate eventDate = LocalDate.parse(request.getEventDate());

        Event event = Event.builder()
            .receiverInfo(receiverInfoService.getReceiverInfo(request.getReceiverInfoId()))
            .eventType(request.getEventType())
            .eventName(request.getEventName())
            .eventDate(eventDate)
            .notificationDaysBefore(request.getNotificationDaysBefore())
            .build();
        repository.save(event);
    }

    @Override
    @Transactional
    public void modifyEvent(EventModifyRequestDto request, long eventId, long loginMemberId) {

        Event event = repository.findByIdAndReceiverInfo_Member_Id(eventId, loginMemberId)
            .orElseThrow(IllegalStateException::new);

        event.setReceiverInfo(receiverInfoService.getReceiverInfo(request.getReceiverInfoId()));
        event.setEventType(request.getEventType());
        event.setEventName(request.getEventName());
        event.setEventDate(LocalDate.parse(request.getEventDate()));
        event.setNotificationDaysBefore(request.getNotificationDaysBefore());

        repository.save(event);
    }
}
