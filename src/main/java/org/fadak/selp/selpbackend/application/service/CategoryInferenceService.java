package org.fadak.selp.selpbackend.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.util.OpenAiBuilderUtil;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleRecommendRequestDto;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryInferenceService {

    private final OpenAiChatModel openAiChatModel;
    private final ObjectMapper objectMapper;

    public Map<String, Integer> distributeBudget(GiftBundleRecommendRequestDto dto) {
        String prompt = OpenAiBuilderUtil.buildBudgetSplitPrompt(dto);

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("gpt-4o")
                .temperature(0.3)
                .maxTokens(512)
                .build();

        Prompt gptPrompt = new Prompt(prompt, options);
        ChatResponse response = openAiChatModel.call(gptPrompt);

        String resultJson = response.getResult().getOutput().getText();

        try {
            System.out.println(resultJson);
            return objectMapper.readValue(resultJson, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}