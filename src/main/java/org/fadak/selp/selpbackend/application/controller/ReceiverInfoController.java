package org.fadak.selp.selpbackend.application.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.ReceiverInfoService;
import org.fadak.selp.selpbackend.domain.dto.request.ReceiverModifyRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.ReceiverRegisterRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.ReceiverInfoListResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
@RestController
@RequestMapping("/receiver-infos")
@RequiredArgsConstructor
public class ReceiverInfoController {

    private final ReceiverInfoService receiverInfoService;

    /**
     * 현재 로그인한 사용자의 주변인 목록 조회
     *
     * @param userPrincipal 현재 로그인한 사용자 정보
     * @return 주변인 정보 리스트
     */
    @GetMapping
    public ResponseEntity<List<ReceiverInfoListResponseDto>> getMyReceiverInfoList(
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        List<ReceiverInfoListResponseDto> receiverInfoList
            = receiverInfoService.getReceiverInfoList(userPrincipal.getId());

        return ResponseEntity.ok(receiverInfoList);
    }

    @GetMapping("/{receiver-info-id}")
    public ResponseEntity<ReceiverInfoListResponseDto> getMyReceiverInfoDetail(
        @PathVariable("receiver-info-id") Long receiverInfoId,
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        ReceiverInfoListResponseDto receiverInfoDetail = receiverInfoService.getReceiverInfoDetail(receiverInfoId, userPrincipal.getId());
        return ResponseEntity.ok(receiverInfoDetail);
    }

    @DeleteMapping("/{receiver-info-id}")
    public ResponseEntity<?> deleteReceiverInfoById(
        @PathVariable("receiver-info-id") Long receiverInfoId
    ) {

        long loginMemberId = 1L;
        receiverInfoService.delete(receiverInfoId, loginMemberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> registerReceiverInfo(
        @RequestBody ReceiverRegisterRequestDto request
    ) {

        long loginMemberId = 1L;
        receiverInfoService.registerReceiverInfo(request, loginMemberId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{receiver-info-id}")
    public ResponseEntity<?> modifyReceiverInfo(
        @RequestBody ReceiverModifyRequestDto request,
        @PathVariable("receiver-info-id") Long receiverInfoId
    ) {

        long loginMemberId = 1L;
        receiverInfoService.modifyReceiverInfo(request, receiverInfoId, loginMemberId);
        return ResponseEntity.ok().build();
    }
}
