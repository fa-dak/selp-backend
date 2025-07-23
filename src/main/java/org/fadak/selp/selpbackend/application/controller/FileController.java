package org.fadak.selp.selpbackend.application.controller;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.FileService;
import org.fadak.selp.selpbackend.domain.constant.FileDir;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{filename:.+}")
    public ResponseEntity<String> download(@PathVariable String filename) {

        String presignedUrl = fileService.getFileUrl(FileDir.ROOT, filename);
        return ResponseEntity.ok(presignedUrl);
    }

    /**
     * MultipartFile 을 받아 MinIO에 저장하고, 저장된 객체의 Presigned URL을 반환합니다.
     *
     * @param file 업로드할 파일
     * @return JSON { "url": "...presigned URL..." }
     */
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {

        String objectName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        fileService.uploadFile(
            file,
            FileDir.ROOT,
            objectName + uuid
        );

        String presignedUrl = fileService.getFileUrl(FileDir.ROOT, objectName);
        return ResponseEntity.ok(Map.of("url", presignedUrl, "filename", objectName));
    }
}