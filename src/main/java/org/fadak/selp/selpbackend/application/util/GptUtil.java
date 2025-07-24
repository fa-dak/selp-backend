package org.fadak.selp.selpbackend.application.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fadak.selp.selpbackend.exception.GptException;
import org.springframework.beans.factory.annotation.Value;
import org.fadak.selp.selpbackend.domain.dto.message.MessageContext;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GptUtil {
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String buildMessagePrompt(MessageContext context) {
        return String.format(
                "Write 3 short, two-line messages in a %s style for a %s in their %d's (%s), " +
                        "as a gift message for giving %s on the occasion of %s. " +
                        "Output in the following format:\n1. message1\n2. message2\n3. message3",
                context.getStyle(),
                context.getGender(),
                context.getAge(),
                context.getRelationship(),
                context.getGiftName(),
                context.getOccasion()
        ) + (context.getAdditionalNote() != null ? " Additional note: " + context.getAdditionalNote() : "");
    }

    public String callGpt(String prompt) {
        try {
            // 1. 요청 바디 구성
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-4.1-nano");
            requestBody.put("messages", List.of(Map.of(
                    "role", "user",
                    "content", prompt
            )));
            requestBody.put("temperature", 0.7);

            // 2. HTTP 요청 구성
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(""))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
                    .build();

            // 3. 요청 보내고 응답 받기
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 4. 응답 파싱
            JsonNode jsonNode = objectMapper.readTree(response.body());
            return jsonNode
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

        } catch (Exception e) {
            throw new GptException("GPT 호출 실패");
        }
    }
}
