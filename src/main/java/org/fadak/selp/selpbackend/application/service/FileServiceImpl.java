package org.fadak.selp.selpbackend.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.util.MiniOUtil;
import org.fadak.selp.selpbackend.domain.constant.FileDir;
import org.fadak.selp.selpbackend.domain.dto.business.UploadResultDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final MiniOUtil fileUtil;

    @Override
    @Transactional
    public UploadResultDto upload(FileDir fileDir, MultipartFile file)
        throws IllegalArgumentException {

        String uuid = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();
        String generatedName = uuid + "_" + fileName;

        try (var is = file.getInputStream()) {
            fileUtil.upload(fileDir, generatedName, is, file.getSize(), file.getContentType());

            return UploadResultDto.builder()
                .presignedUrl(fileUtil.getPresignedUrl(fileDir, generatedName))
                .filePath(fileDir.getValue() + generatedName)
                .build();
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    @Override
    public String getUrl(FileDir fileDir, String objectName) {

        return fileUtil.getPresignedUrl(fileDir, objectName);
    }

    @Override
    @Transactional
    public void delete(String filePath) {

        fileUtil.delete(filePath);
    }

    @Override
    @Transactional
    public UploadResultDto modify(String oldFilePath, FileDir newFileDir,
        MultipartFile newFile) {

        String uuid = UUID.randomUUID().toString();
        String fileName = newFile.getOriginalFilename();
        String generatedName = uuid + "_" + fileName;

        try (var is = newFile.getInputStream()) {
            String presignedUrl = fileUtil.modify(
                oldFilePath,
                newFileDir,
                generatedName,
                is,
                newFile.getSize(),
                newFile.getContentType());

            return UploadResultDto.builder()
                .presignedUrl(presignedUrl)
                .filePath(newFileDir.getValue() + generatedName)
                .build();
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}