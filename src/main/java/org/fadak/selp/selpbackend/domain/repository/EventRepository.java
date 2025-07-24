package org.fadak.selp.selpbackend.domain.repository;

import java.time.LocalDate;
import java.util.List;
import org.fadak.selp.selpbackend.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventDateBetweenAndReceiverInfo_Member_Id(
        LocalDate startDate,
        LocalDate endDate,
        long memberId);

    void deleteByIdAndReceiverInfo_Member_Id(Long id, Long receiverInfoMemberId);
}