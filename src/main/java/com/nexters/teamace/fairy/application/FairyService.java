package com.nexters.teamace.fairy.application;

import com.nexters.teamace.chat.application.AllChatQuery;
import com.nexters.teamace.chat.application.AllChatResult;
import com.nexters.teamace.chat.application.ChatRoomService;
import com.nexters.teamace.chat.domain.MessageType;
import com.nexters.teamace.common.infrastructure.annotation.ReadOnlyTransactional;
import com.nexters.teamace.common.presentation.UserInfo;
import com.nexters.teamace.conversation.application.ConversationContext;
import com.nexters.teamace.conversation.application.ConversationService;
import com.nexters.teamace.conversation.domain.ConversationContextType;
import com.nexters.teamace.conversation.domain.ConversationType;
import com.nexters.teamace.conversation.domain.EmotionSelectConversation;
import com.nexters.teamace.emotion.domain.EmotionType;
import com.nexters.teamace.fairy.application.dto.FairyInfo;
import com.nexters.teamace.fairy.domain.Fairy;
import com.nexters.teamace.fairy.domain.FairyBook;
import com.nexters.teamace.fairy.domain.FairyBookEntry;
import com.nexters.teamace.fairy.domain.FairyRepository;
import com.nexters.teamace.fairy.infrastructure.AcquiredFairyEntity;
import com.nexters.teamace.fairy.infrastructure.AcquiredFairyJpaRepository;
import com.nexters.teamace.fairy.infrastructure.dto.FairyProjection;

import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FairyService {

    private final ConversationService conversationService;
    private final ChatRoomService chatRoomService;
    private final FairyRepository fairyRepository;
    private final AcquiredFairyJpaRepository acquiredFairyJpaRepository;

    private static final String EMOTION_NAMES_STRING = EmotionType.getEmotionNames().toString();

    @ReadOnlyTransactional
    public FairyResult getFairy(UserInfo user, Long chatRoomId) {
        List<String> conversationHistory = getConversationHistory(user, chatRoomId);

        EmotionSelectConversation emotionSelectConversation = analyzeEmotions(conversationHistory);

        List<FairyProjection> fairyProjections = findFairyCandidates(emotionSelectConversation);

        List<FairyInfo> fairyInfos = convertToFairyInfo(fairyProjections);

        return new FairyResult(fairyInfos);
    }

    private List<String> getConversationHistory(UserInfo user, Long chatRoomId) {
        AllChatResult allChats =
                chatRoomService.getAllChats(new AllChatQuery(chatRoomId, user.userId()));

        return allChats.chats().stream()
                .sorted(Comparator.comparing(AllChatResult.ChatResult::chatId))
                .map(
                        chat -> {
                            String prefix = chat.type() == MessageType.USER ? "[사용자] " : "[상담가] ";
                            return prefix + chat.message();
                        })
                .toList();
    }

    private EmotionSelectConversation analyzeEmotions(List<String> conversationHistory) {
        ConversationType type = ConversationType.EMOTION_ANALYSIS;

        Map<ConversationContextType, String> variables =
                Map.of(
                        ConversationContextType.PREVIOUS_CONVERSATIONS,
                        String.join("\n", conversationHistory),
                        ConversationContextType.EMOTION_CANDIDATES,
                        EMOTION_NAMES_STRING);
        ConversationContext context = new ConversationContext("질의응답", variables);

        return (EmotionSelectConversation) conversationService.chat(type.getType(), type, context);
    }

    private List<FairyProjection> findFairyCandidates(
            EmotionSelectConversation emotionSelectConversation) {
        var emotions = emotionSelectConversation.emotions().stream()
            .map(e -> EmotionType.valueOf(e.name()))
            .distinct()
            .toList();
        if (emotions.isEmpty()) {
            return List.of();
        }
        return fairyRepository.findAllByEmotionNames(emotions);
    }

    private List<FairyInfo> convertToFairyInfo(List<FairyProjection> fairyProjections) {
        return fairyProjections.stream()
                .map(
                        p ->
                                new FairyInfo(
                                        p.id(),
                                        p.name(),
                                        p.image(),
                                        p.silhouetteImage(),
                                        p.emotion().getDescription()))
                .toList();
    }

    @ReadOnlyTransactional
    public FairyBook getFairyBook(Long userId) {
        List<Fairy> allFairies = fairyRepository.findAll();
        Set<Long> acquiredFairyIds =
                acquiredFairyJpaRepository.findAllByUserId(userId).stream()
                        .map(AcquiredFairyEntity::getFairyId)
                        .collect(Collectors.toSet());

        List<FairyBookEntry> fairyBookEntries =
                allFairies.stream()
                        .map(
                                fairy ->
                                        new FairyBookEntry(
                                                fairy, acquiredFairyIds.contains(fairy.getId())))
                        .toList();

        return new FairyBook(fairyBookEntries);
    }
}
