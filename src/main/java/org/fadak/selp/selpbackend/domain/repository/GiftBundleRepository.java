package org.fadak.selp.selpbackend.domain.repository;

import org.fadak.selp.selpbackend.domain.entity.GiftBundle;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftBundleRepository extends JpaRepository<GiftBundle, Long> {

    /**
     * fetch join 으로 GiftBundle, Event, ReceiverInfo를 한번에 조회
     * @param memberId
     * @param sort
     * @return
     */
    @Query("SELECT gb " +
            "FROM GiftBundle gb " +
            "JOIN FETCH gb.event e " +
            "JOIN FETCH e.receiverInfo r " +
            "WHERE gb.member.id = :memberId")
    List<GiftBundle> findAllByMemberIdWithDetails(@Param("memberId") Long memberId, Sort sort);
}