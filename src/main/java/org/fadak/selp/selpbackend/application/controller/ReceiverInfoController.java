package org.fadak.selp.selpbackend.application.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.ReceiverInfoService;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.fadak.selp.selpbackend.domain.dto.request.ReceiverModifyRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.ReceiverRegisterRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.ReceiverInfoListResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receiver-infos")
@RequiredArgsConstructor
public class ReceiverInfoController {

    private final ReceiverInfoService receiverInfoService;

    /**
     * 받는 사람 목록 조회/검색
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ReceiverInfoListResponseDto>> searchReceiverInfoList(
        @AuthenticationPrincipal UserPrincipal userPrincipal

    ) {

        long loginMemberId = userPrincipal.getId();
        List<ReceiverInfoListResponseDto> receiverInfoList
            = receiverInfoService.getReceiverInfoList(loginMemberId);

        return ResponseEntity.ok(receiverInfoList);
    }

    @DeleteMapping("/{receiver-info-id}")
    public ResponseEntity<?> deleteReceiverInfoById(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable("receiver-info-id") Long receiverInfoId
    ) {

        long loginMemberId = userPrincipal.getId();
        receiverInfoService.delete(receiverInfoId, loginMemberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> registerReceiverInfo(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @Valid @RequestBody ReceiverRegisterRequestDto request
    ) {

        long loginMemberId = userPrincipal.getId();
        receiverInfoService.registerReceiverInfo(request, loginMemberId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{receiver-info-id}")
    public ResponseEntity<?> modifyReceiverInfo(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @Valid @RequestBody ReceiverModifyRequestDto request,
        @PathVariable("receiver-info-id") Long receiverInfoId
    ) {

        long loginMemberId = userPrincipal.getId();
        receiverInfoService.modifyReceiverInfo(request, receiverInfoId, loginMemberId);
        return ResponseEntity.ok().build();
    }
}
