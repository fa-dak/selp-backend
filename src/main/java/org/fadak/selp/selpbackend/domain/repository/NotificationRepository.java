package org.fadak.selp.selpbackend.domain.repository;

import org.fadak.selp.selpbackend.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findBySendDateAndIsSent(LocalDate sendDate, Boolean isSent);

    List<Notification> findAllByMemberId(Long memberId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.member.id = :memberId AND n.isRead = false")
    int countUnreadNotifications(@Param("memberId") Long memberId);

    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.member.id = :memberId AND n.isRead = false")
    void readAllNotificationByMemberId(Long memberId);
}