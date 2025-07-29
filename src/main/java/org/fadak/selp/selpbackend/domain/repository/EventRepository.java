package org.fadak.selp.selpbackend.domain.repository;

import org.fadak.selp.selpbackend.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventDateBetweenAndReceiverInfo_Member_Id(
            LocalDate startDate,
            LocalDate endDate,
            long memberId);

    void deleteByIdAndReceiverInfo_Member_Id(Long id, Long receiverInfoMemberId);

    Optional<Event> findByIdAndReceiverInfo_Member_Id(Long id, Long receiverInfoMemberId);

    Optional<Event> findById(Long id);

    @Query("SELECT e FROM Event e WHERE e.receiverInfo.member.id = :memberId AND e.eventDate > :today ORDER BY e.eventDate ASC")
    List<Event> findTop5UpcomingEvents(@Param("memberId") Long memberId, @Param("today") LocalDate today);
    ;
}