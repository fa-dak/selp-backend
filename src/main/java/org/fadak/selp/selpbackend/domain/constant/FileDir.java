package org.fadak.selp.selpbackend.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 파일 저장 디렉터리 정보
 */
@Getter
@AllArgsConstructor
public enum FileDir {

    ROOT("/"),// 루트: 테스트 용
    PRODUCT_IMG("/product-img/"); // 상품 이미지

    // 필요한 것 추가해주세요.
    private final String value;

}
