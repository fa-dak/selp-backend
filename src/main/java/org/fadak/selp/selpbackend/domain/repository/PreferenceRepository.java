package org.fadak.selp.selpbackend.domain.repository;

import org.fadak.selp.selpbackend.domain.entity.Preference;
import org.fadak.selp.selpbackend.domain.entity.ReceiverInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Optional<Preference> findByReceiverInfo(ReceiverInfo receiverInfo);

    @Modifying
    void deleteAllByReceiverInfoId(Long receiverInfoId);
}
