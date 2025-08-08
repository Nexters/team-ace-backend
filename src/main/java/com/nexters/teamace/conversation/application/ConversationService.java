package com.nexters.teamace.conversation.application;

import com.nexters.teamace.conversation.domain.ConversationScript;
import com.nexters.teamace.conversation.domain.ConversationType;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationClient conversationClient;
    private final ConversationScriptService conversationScriptService;

    public <T> T chat(
            final Class<T> type,
            final ConversationType conversationType,
            final ConversationContext context) {
        return chat(type, conversationType, context, ".");
    }

    public <T> T chat(
            final Class<T> type,
            final ConversationType conversationType,
            final ConversationContext context,
            final String userMessage) {
        final ConversationScript script =
                conversationScriptService.getPromptTemplate(conversationType);

        script.validateVariables(context.variables());
        final String renderedScript = script.render(context.variables());

        return conversationClient.chat(
                type, renderedScript, Objects.isNull(userMessage) ? "" : userMessage);
    }
}
