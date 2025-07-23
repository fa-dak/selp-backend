package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.constant.FileDir;
import org.springframework.web.multipart.MultipartFile;

/**
 * MinIO 연동 파일 서비스
 */
public interface FileService {

    /**
     * 객체를 업로드하고, 바로 접근 가능한 Presigned URL을 반환합니다.
     *
     * @param file       업로드할 파일
     * @param objectName 버킷 내 저장될 객체 키 (예: "prod/path/to/file.txt")
     * @return Presigned GET URL
     */
    String uploadFile(MultipartFile file, FileDir fileDir, String objectName);

    /**
     * 이미 업로드된 객체의 Presigned GET URL을 반환합니다.
     *
     * @param objectName 버킷 내 객체 키
     */
    String getFileUrl(FileDir fileDir, String objectName);

    /**
     * 객체를 삭제합니다.
     *
     * @param objectName 버킷 내 객체 키
     */
    void deleteFile(FileDir fileDir, String objectName);

    /**
     * 기존 객체를 새로운 파일로 덮어쓰고, 새롭게 생성된 Presigned URL을 반환합니다.
     *
     * @param objectName 버킷 내 객체 키
     * @param file       덮어쓸 파일
     */
    String modifyFile(FileDir fileDir, String objectName, MultipartFile file);
}