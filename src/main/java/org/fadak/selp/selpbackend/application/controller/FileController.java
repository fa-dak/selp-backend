//package org.fadak.selp.selpbackend.application.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.fadak.selp.selpbackend.application.service.FileService;
//import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
//import org.fadak.selp.selpbackend.domain.constant.FileDir;
//import org.fadak.selp.selpbackend.domain.dto.business.UploadResultDto;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
/// **
// * 파일 예제
// */
//@RestController
//@RequestMapping("/files")
//@RequiredArgsConstructor
//public class FileController {
//
//    private final FileService fileService;
//
//    @GetMapping("/{filename:.+}")
//    public ResponseEntity<String> download(
//        @AuthenticationPrincipal UserPrincipal userPrincipal,
//        @PathVariable String filename
//    ) {
//
//        String presignedUrl = fileService.getUrl(FileDir.ROOT, filename);
//        return ResponseEntity.ok(presignedUrl);
//    }
//
//    /**
//     * MultipartFile 을 받아 MinIO에 저장하고, 저장된 객체의 Presigned URL을 반환합니다.
//     *
//     * @param file 업로드할 파일
//     * @return JSON { "url": "...presigned URL..." }
//     */
//    @PostMapping(
//        value = "/upload",
//        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> upload(
//        @AuthenticationPrincipal UserPrincipal userPrincipal,
//        @RequestParam("file") MultipartFile file
//    ) {
//
//        UploadResultDto uploadResultDto = fileService.upload(
//            FileDir.ROOT,
//            file
//        );
//
//        return ResponseEntity.ok(uploadResultDto);
//    }
//
//}