package org.fadak.selp.selpbackend.application.util;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import org.fadak.selp.selpbackend.domain.constant.FileDir;
import org.fadak.selp.selpbackend.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MiniOUtil implements FileUtil {

    private final MinioClient minioClient;
    private final String bucketName;
    private final int expirationSeconds;

    public MiniOUtil(
        MinioClient minioClient,
        @Value("${minio.bucket}") String bucketName,
        @Value("${minio.presign.expiration-seconds}") int expirationSeconds
    ) {

        this.minioClient = minioClient;
        this.bucketName = bucketName;
        this.expirationSeconds = expirationSeconds;
    }

    /**
     * 지정한 객체에 대해 GET Presigned URL 을 생성합니다.
     */
    @Override
    public String getPresignedUrl(FileDir fileDir, String objectName) {

        try {
            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(fileDir.getValue() + objectName)
                    .expiry(expirationSeconds, TimeUnit.SECONDS)
                    .build()
            );
        } catch (Exception e) {
            throw new FileStorageException("Failed to generate presigned URL for " + objectName, e);
        }
    }

    /**
     * 객체를 업로드합니다.
     *
     * @param objectName  버킷 내 저장될 객체 키
     * @param content     업로드할 데이터의 InputStream
     * @param size        데이터 길이(byte)
     * @param contentType MIME 타입 (예: "image/png", "text/plain")
     */
    @Override
    public void upload(
        FileDir fileDir,
        String objectName,
        InputStream content,
        long size,
        String contentType
    ) {

        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileDir.getValue() + objectName)
                    .stream(content, size, -1)
                    .contentType(contentType)
                    .build()
            );
        } catch (Exception e) {
            throw new FileStorageException("Failed to upload object " + objectName, e);
        }
    }

    /**
     * 객체를 삭제합니다.
     *
     * @param objectName 버킷 내 객체 키
     */
    @Override
    public void delete(FileDir fileDir, String objectName) {

        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(fileDir.getValue() + bucketName)
                    .object(objectName)
                    .build()
            );
        } catch (Exception e) {
            throw new FileStorageException("Failed to delete object " + objectName, e);
        }
    }

    /**
     * 기존 객체를 새 데이터로 덮어쓰고, 새로운 Presigned URL을 반환합니다.
     *
     * @param objectName  버킷 내 객체 키
     * @param content     덮어쓸 데이터의 InputStream
     * @param size        데이터 길이(byte)
     * @param contentType MIME 타입
     * @return 새 Presigned URL
     */
    @Override
    public String modify(
        FileDir fileDir,
        String objectName,
        InputStream content,
        long size,
        String contentType
    ) {

        try {
            upload(fileDir, objectName, content, size, contentType);
            return getPresignedUrl(fileDir, objectName);
        } catch (FileStorageException e) {
            throw e;
        } catch (Exception e) {
            throw new FileStorageException("Failed to modify object " + objectName, e);
        }
    }
}