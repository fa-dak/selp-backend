package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.constant.FileDir;
import org.fadak.selp.selpbackend.domain.dto.business.UploadResultDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * MinIO 연동 파일 서비스
 */
public interface FileService {

    /**
     * 객체를 업로드하고, 바로 접근 가능한 Presigned URL을 반환합니다.
     *
     * @param file 업로드할 파일
     * @return Presigned GET URL
     */
    UploadResultDto upload(FileDir fileDir, MultipartFile file);

    /**
     * 이미 업로드된 객체의 Presigned GET URL을 반환합니다.
     *
     * @param objectName 버킷 내 객체 키
     */
    String getUrl(FileDir fileDir, String objectName);

    /**
     * 객체를 삭제합니다.
     */
    void delete(String filePath);

    /**
     * 기존 객체를 새로운 파일로 덮어쓰고, 새롭게 생성된 Presigned URL을 반환합니다.
     *
     * @param file 덮어쓸 파일
     */
    UploadResultDto modify(String originalFilePath, FileDir newFileDir, MultipartFile file);
}