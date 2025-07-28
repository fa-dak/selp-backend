package org.fadak.selp.selpbackend.domain.repository;

import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiverInfoRepository extends JpaRepository<ReceiverInfo, Long> {

    void deleteByIdAndMember_Id(long id, long memberId);

    Optional<ReceiverInfo> findByIdAndMember_Id(Long receiverInfoId, long memberId);

    @EntityGraph(attributePaths = "preferences")
    List<ReceiverInfo> findAllByMemberId(Long memberId);
}