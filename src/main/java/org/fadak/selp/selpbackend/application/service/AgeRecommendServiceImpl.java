package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.dto.response.AgeRecommendResponseDto;
import org.fadak.selp.selpbackend.domain.entity.AgeRecommendation;
import org.fadak.selp.selpbackend.domain.repository.AgeRecommendationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgeRecommendServiceImpl implements AgeRecommendService {

    private final ElasticEmbeddingSearchService elasticSearchService;
    private final AgeRecommendationRepository repository;

    private static final Map<String, String> AGE_PROMPTS = Map.of(
            "10s", "트렌디하고 감성적인 10대들을 위한 인기 선물 추천. 학생들이 좋아할 만한 귀엽고 저렴한 선물",
            "20s", "20대 대학생과 사회 초년생이 선호하는 스타일리시하고 실용적인 선물",
            "30s", "30대 직장인과 부모들이 좋아할 수 있는 고급스럽고 실용적인 선물",
            "40s", "건강과 라이프스타일을 중시하는 40대를 위한 실용적이고 의미 있는 선물",
            "50s", "50대 부모님께 드리기 좋은 감사와 마음이 담긴 따뜻한 선물"
    );

    @Override
    @Transactional
    public void generateAndSaveAllRecommendations() {
        for (String ageGroup : AGE_PROMPTS.keySet()) {
            String prompt = AGE_PROMPTS.get(ageGroup);
            List<Map<String, Object>> products = elasticSearchService.searchByUserInput(prompt, 12, null, 0);

            repository.deleteByAgeGroup(ageGroup); // 이전 추천 삭제

            List<AgeRecommendation> toSave = products.stream()
                    .map(item -> {
                        AgeRecommendation rec = new AgeRecommendation();
                        rec.setName(item.get("name").toString());
                        rec.setPrice(Long.parseLong(item.get("price").toString()));
                        rec.setImagePath(item.get("image_path").toString());
                        rec.setDetailPath(item.get("detail_path").toString());
                        rec.setAgeGroup(ageGroup);
                        return rec;
                    })
                    .toList();

            repository.saveAll(toSave);
        }
    }

    @Override
    public List<AgeRecommendResponseDto> getAgeRecommendProductList() {
        return repository.findAll().stream()
                .map(rec -> AgeRecommendResponseDto.builder()
                        .id(rec.getId())
                        .name(rec.getName())
                        .price(rec.getPrice())
                        .imageUrl(rec.getImagePath())
                        .detailPath(rec.getDetailPath())
                        .ageGroup(rec.getAgeGroup())
                        .build())
                .toList();
    }
}





