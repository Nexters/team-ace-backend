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
import com.nexters.teamace.fairy.application.dto.FairyInfo;
import com.nexters.teamace.fairy.domain.Fairy;
import com.nexters.teamace.fairy.domain.FairyBook;
import com.nexters.teamace.fairy.domain.FairyBookEntry;
import com.nexters.teamace.fairy.domain.FairyRepository;
import com.nexters.teamace.fairy.infrastructure.AcquiredFairyEntity;
import com.nexters.teamace.fairy.infrastructure.AcquiredFairyJpaRepository;
import com.nexters.teamace.fairy.infrastructure.dto.FairyProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FairyService {

    private final ConversationService conversationService;
    private final ChatRoomService chatRoomService;
    private final FairyRepository fairyRepository;
    private final AcquiredFairyJpaRepository acquiredFairyJpaRepository;

    @ReadOnlyTransactional
    public FairyResult getFairy(UserInfo user, Long chatRoomId) {
        AllChatResult allChats =
            chatRoomService.getAllChats(new AllChatQuery(chatRoomId, user.userId()));

        List<String> conversationHistory =
            allChats.chats().stream()
                .sorted(Comparator.comparing(AllChatResult.ChatResult::chatId))
                .map(
                    chat -> {
                        String prefix =
                            chat.type() == MessageType.USER ? "사용자: " : "상담가: ";
                        return prefix + chat.message();
                    })
                .toList();
        ConversationType type = ConversationType.EMOTION_ANALYSIS;

        Map<ConversationContextType, String> variables =
            Map.of(
                ConversationContextType.PREVIOUS_CONVERSATIONS,
                String.join("\n", conversationHistory) // This should be populated from chat room history
            );
        ConversationContext context = new ConversationContext("질의응답", variables);

        // 2. 질의응답 기반으로 ai call 해서 감정 후보 획득
        EmotionSelectConversation emotionSelectConversation =
            (EmotionSelectConversation) conversationService.chat(type.getType(), type, context);

        // 3. 감정 후보 기반으로 fairy 후보 조회
        List<FairyProjection> fairyProjections =
            fairyRepository.findAllByEmotionNames(
                emotionSelectConversation.emotions().stream()
                    .map(EmotionSelectConversation.Emotions::name)
                    .toList());

        // 4. FairyInfo로 변환
        List<FairyInfo> fairyInfos =
            fairyProjections.stream()
                .map(
                    p ->
                        new FairyInfo(
                            p.id(),
                            p.name(),
                            p.image(),
                            p.silhouetteImage(),
                            p.emotion()))
                .toList();

        // 5. FairyResult에 맞게 정제하여 반환
        return new FairyResult(fairyInfos);
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
