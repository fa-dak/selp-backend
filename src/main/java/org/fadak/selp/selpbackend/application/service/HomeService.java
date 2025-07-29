package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.dto.response.HomeResponseDto;

public interface HomeService {
    HomeResponseDto getHome(Long memberId);
}
