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
                "Write 3 short and warm two-line gift messages in a %s style for a %d-year-old %s (%s). " +
                        "The messages are for giving a gift (%s) on the occasion of %s. " +
                        "%s\n\n" +
                        "Output only the messages in this format:\n" +
                        "1. message1\n2. message2\n3. message3",
                context.getStyle(),
                context.getAge(),
                context.getGender().equalsIgnoreCase("M") ? "man" : "woman",
                context.getRelationship(),
                context.getGiftCategory(),
                context.getOccasion(),
                context.getAdditionalNote() != null ?
                        "Consider this additional context: " + context.getAdditionalNote() :
                        ""
        );
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
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
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
            throw new GptException("GPT 호출 실패 "+e.getMessage());
        }
    }
}
