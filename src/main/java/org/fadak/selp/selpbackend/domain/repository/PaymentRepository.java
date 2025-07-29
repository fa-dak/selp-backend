package org.fadak.selp.selpbackend.domain.repository;

import org.fadak.selp.selpbackend.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
