package org.fadak.selp.selpbackend.domain.repository;

import java.util.List;
import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverInfoRepository extends JpaRepository<ReceiverInfo, Long> {

    List<ReceiverInfo> findAllByMemberId(long memberId);

    void deleteByIdAndMember_Id(long id, long memberId);
}