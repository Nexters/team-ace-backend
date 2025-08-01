package com.nexters.teamace.fairy.application;

import com.nexters.teamace.chat.application.ChatRoomService;
import com.nexters.teamace.common.infrastructure.annotation.ReadOnlyTransactional;
import com.nexters.teamace.common.presentation.UserInfo;
import com.nexters.teamace.conversation.application.ConversationContext;
import com.nexters.teamace.conversation.application.ConversationService;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FairyService {

    private final ConversationService conversationService;
    private final ChatRoomService chatRoomService;
    private final FairyRepository fairyRepository;
    private final AcquiredFairyJpaRepository acquiredFairyJpaRepository;

    @ReadOnlyTransactional
    public FairyResult getFairy(UserInfo user, Long chatRoomId) {
        /* 1. chatRoomId 기반으로 chatRoom의 모든 질의응답 조회 (TODO)
         * context 데이터는 아래와 같이 구성해서 던져줄지?
         * 1. 상담가: 데이터1
         * 2. 사용자: 데이터2
         * 3. 상담가: 데이터3
         * 4. ...
         * */
        ConversationType type = ConversationType.EMOTION_ANALYSIS;
        ConversationContext context = new ConversationContext("질의응답", List.of());

        // 2. 질의응답 기반으로 ai call 해서 감정 후보 획득
        EmotionSelectConversation emotionSelectConversation =
                (EmotionSelectConversation)
                        conversationService.chat(type.getType(), type, context, "");

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
