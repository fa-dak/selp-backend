package org.fadak.selp.selpbackend.application.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.dto.request.EventListSearchRequestDto;
import org.fadak.selp.selpbackend.domain.entity.Event;
import org.fadak.selp.selpbackend.domain.repository.EventRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository repository;

    @Override
    public List<Event> getEventList(EventListSearchRequestDto request, long loginMemberId) {

        YearMonth yearMonth = YearMonth.of(request.getYear(), request.getMonth());
        LocalDate startDate = yearMonth.atDay(1); // 2025-07-01
        LocalDate endDate = yearMonth.atEndOfMonth(); // 2025-07-31

        return repository.findByEventDateBetweenAndReceiverInfo_Member_Id(
            startDate,
            endDate,
            loginMemberId);
    }
}
