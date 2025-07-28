package org.fadak.selp.selpbackend.domain.repository;


import org.fadak.selp.selpbackend.domain.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByMemberId(Long memberId);

}