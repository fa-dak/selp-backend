package org.fadak.selp.selpbackend.domain.dto.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
/**
 * String presignedUrl: 클라이언트에게 넘겨줄 파일 접근 url
 * String filePath: DB에 저장된/저장할 파일 경로 정보
 */
public class UploadResultDto {

    private String presignedUrl;

    private String filePath;
}
