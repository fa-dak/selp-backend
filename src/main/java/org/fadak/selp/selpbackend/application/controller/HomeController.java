package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.HomeService;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.fadak.selp.selpbackend.domain.dto.response.HomeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/main")
    public ResponseEntity<HomeResponseDto> getHomeData(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(homeService.getHome(userPrincipal.getId()));
    }
}