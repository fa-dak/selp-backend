package org.fadak.selp.selpbackend.domain.repository;

import org.fadak.selp.selpbackend.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderAndEmail(String provider, String email);

    Optional<Member> findByEmail(String email);
}