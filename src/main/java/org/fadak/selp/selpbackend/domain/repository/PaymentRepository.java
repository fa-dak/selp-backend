package org.fadak.selp.selpbackend.domain.repository;

import java.util.Optional;
import org.fadak.selp.selpbackend.domain.constant.PayStatus;
import org.fadak.selp.selpbackend.domain.entity.GiftBundle;
import org.fadak.selp.selpbackend.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByGiftBundleAndStatus(GiftBundle giftBundle, PayStatus status);
}
