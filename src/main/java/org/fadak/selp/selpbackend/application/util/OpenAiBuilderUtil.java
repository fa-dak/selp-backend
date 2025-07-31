package org.fadak.selp.selpbackend.application.util;

import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleRecommendRequestDto;

public class OpenAiBuilderUtil {

    public static String buildBudgetSplitPrompt(GiftBundleRecommendRequestDto dto) {
        return """
                아래는 선물 구성 조건입니다:
                - 총 예산: %d원
                - 연령: %s
                - 성별: %s
                - 관계: %s
                - 기념일: %s
                - 사용자 메시지: "%s"
                - 선택한 카테고리: %s

                각 카테고리에 대해 적절한 예산을 분배해주세요. JSON 형식으로 아래와 같이 반환하세요:
                오직 JSON만 출력하세요. ``` json ``` 이런거도 다 빼고 오로지 아래 양식으로만

                {
                  "fashion": 30000,
                  "flower": 20000,
                  "desert": 10000
                }
                """.formatted(
                dto.getBudget(),
                dto.getAgeRange() + "세",
                dto.getGender().getValue(),
                dto.getRelation(),
                dto.getAnniversaryType().getValue(),
                dto.getUserMessage(),
                dto.getCategories()
        );
    }

    public static String buildEmbeddingPrompt(GiftBundleRecommendRequestDto requestDto, String category, int budget) {
        return String.format(
                "%d대 %s %s를 위한 선물입니다. "
                        + "기념일은 %s이고, 예산은 %d원입니다. "
                        + "특징은 %s입니다.",
                requestDto.getAgeRange(),
                requestDto.getGender().getValue(),
                requestDto.getRelation(),
                requestDto.getAnniversaryType().getValue(),
                budget,
                requestDto.getUserMessage()
        );
    }
}
