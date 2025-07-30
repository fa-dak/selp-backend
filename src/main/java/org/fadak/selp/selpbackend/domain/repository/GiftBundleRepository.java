package org.fadak.selp.selpbackend.domain.repository;

import java.util.List;
import java.util.Optional;
import org.fadak.selp.selpbackend.domain.entity.GiftBundle;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftBundleRepository extends JpaRepository<GiftBundle, Long> {

    /**
     * fetch join 으로 GiftBundle, Event, ReceiverInfo를 한번에 조회
     *
     * @param memberId
     * @param sort
     * @return
     */
    @Query("SELECT DISTINCT gb " +
        "FROM GiftBundle gb " +
        "LEFT JOIN FETCH gb.event e " +
        "LEFT JOIN FETCH e.receiverInfo r " +
        "LEFT JOIN FETCH gb.giftBundleItems gbi " +
        "LEFT JOIN FETCH gbi.product p " +
        "WHERE gb.member.id = :memberId")
    List<GiftBundle> findAllByMemberIdWithDetails(@Param("memberId") Long memberId, Sort sort);

    @Query("SELECT gb " +
        "FROM GiftBundle gb " +
        "LEFT JOIN FETCH gb.event e " +
        "LEFT JOIN FETCH e.receiverInfo r " +
        "LEFT JOIN FETCH gb.giftBundleItems gbi " +
        "LEFT JOIN FETCH gbi.product p " +
        "WHERE gb.id = :bundleId AND gb.member.id = :memberId")
    Optional<GiftBundle> findDetailsByIdAndMemberId(@Param("bundleId") Long bundleId,
        @Param("memberId") Long memberId);

    Optional<GiftBundle> findTopByMemberIdOrderByCreatedDateDesc(Long memberId);

    Optional<GiftBundle> findByIdAndMember_Id(Long id, Long memberId);
}
