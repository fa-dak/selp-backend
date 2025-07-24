package org.fadak.selp.selpbackend.domain.repository;

import java.time.LocalDate;
import java.util.List;
import org.fadak.selp.selpbackend.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // date: YYYY-MM-DD 이고 eventDate는 LocalDate
    List<Event> findByEventDateAndReceiverInfo_Member_Id(LocalDate date, long loginMemberId);
}