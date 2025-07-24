package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.domain.entity.Member;
import org.fadak.selp.selpbackend.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;

    @Override
    public Member getMember(long memberId) {

        return repository.findById(memberId)
            .orElseThrow(IllegalArgumentException::new);
    }
}
