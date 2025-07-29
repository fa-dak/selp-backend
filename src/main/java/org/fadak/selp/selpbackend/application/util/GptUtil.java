package org.fadak.selp.selpbackend.application.util;

import java.util.List;
import org.fadak.selp.selpbackend.domain.constant.Gender;
import org.fadak.selp.selpbackend.domain.dto.message.MessageContext;
import org.fadak.selp.selpbackend.exception.GptException;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;

public class GptUtil {

    public static String buildMessagePrompt(MessageContext context) {

        return String.format(
            "Write 3 short and warm two-line gift messages in a %s style for a %d-year-old %s (%s). "
                +
                "The messages are for giving a gift on the occasion of %s.\n\n" +
                "Output only the messages in this format:\n" +
                "1. message1\n2. message2\n3. message3",
            context.getStyle(),
            context.getAge(),
            context.getGender().equals(Gender.MALE) ? "man" : "woman",
            context.getRelationship(),
            context.getOccasion()
        );
    }

    public static String callGpt(ChatModel chatModel, String userText) {

        try {
            // 1. 메시지 생성
            UserMessage userMessage = new UserMessage(userText);

            // 2. 옵션 설정
            OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("gpt-4.1-nano")
                .temperature(0.7)
                .build();

            // 3. 요청 구성
            Prompt prompt = new Prompt(List.of(userMessage), options);

            // 4. 호출
            return chatModel.call(prompt).getResult().getOutput().getText();
        } catch (Exception e) {
            throw new GptException("GPT 호출 실패: " + e.getMessage());
        }
    }
}
