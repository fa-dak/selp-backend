package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.util.FileUtil;
import org.fadak.selp.selpbackend.domain.constant.FileDir;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileUtil fileUtil;

    /**
     * 파일을 업로드하고 Presigned GET URL을 반환합니다.
     *
     * @param file       업로드할 파일
     * @param objectName 버킷 내 저장될 객체 키 (ex: "prod/test.txt")
     * @return Presigned URL
     */
    @Override
    public String uploadFile(MultipartFile file, FileDir fileDir, String objectName)
        throws IllegalArgumentException {

        try (var is = file.getInputStream()) {
            fileUtil.upload(fileDir, objectName, is, file.getSize(), file.getContentType());
            return fileUtil.getPresignedUrl(fileDir, objectName);
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    /**
     * 업로드된 객체의 Presigned GET URL을 반환합니다.
     */
    @Override
    public String getFileUrl(FileDir fileDir, String objectName) {

        return fileUtil.getPresignedUrl(fileDir, objectName);
    }

    /**
     * 객체를 삭제합니다.
     */
    @Override
    public void deleteFile(FileDir fileDir, String objectName) {

        fileUtil.delete(fileDir, objectName);
    }

    /**
     * 기존 객체를 새 파일로 덮어쓰고, 새로운 Presigned URL을 반환합니다.
     */
    @Override
    public String modifyFile(FileDir fileDir, String objectName, MultipartFile file) {

        try (var is = file.getInputStream()) {
            return fileUtil.modify(fileDir, objectName, is, file.getSize(),
                file.getContentType());
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}