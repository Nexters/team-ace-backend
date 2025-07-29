package com.nexters.teamace.fairy.application;

import com.nexters.teamace.chat.application.ChatRoomService;
import com.nexters.teamace.conversation.application.ConversationContext;
import com.nexters.teamace.conversation.application.ConversationService;
import com.nexters.teamace.conversation.domain.ConversationType;
import com.nexters.teamace.conversation.domain.EmotionSelectConversation;
import com.nexters.teamace.emotion.application.EmotionService;
import com.nexters.teamace.emotion.domain.Emotion;
import com.nexters.teamace.fairy.domain.Fairy;
import com.nexters.teamace.fairy.domain.FairyRepository;
import com.nexters.teamace.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FairyService {

    private final ConversationService conversationService;
    private final ChatRoomService chatRoomService;
    private final EmotionService emotionService;
    private final FairyRepository fairyRepository;

    public FairyResult getFairy(User user, Long chatRoomId) {
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

        // 3. 감정 후보 기반으로 emotion 조회
        List<Emotion> emotions =
                emotionService.findAllByNamesIn(
                        emotionSelectConversation.emotions().stream()
                                .map(EmotionSelectConversation.Emotions::name)
                                .toList());

        // 4. emotion 기반으로 fairy 조회 (fairy -> emotion의 단방향 참조를 유지하고 싶다면?)
        List<Fairy> fairies =
                fairyRepository.findAllByEmotionIdIn(
                        emotions.stream().map(Emotion::getId).toList());

        // 5. FairyResult에 맞게 정제하여 반환
        return new FairyResult(
                fairies.stream()
                        .map(
                                f ->
                                        new FairyCandidate(
                                                f.getId(),
                                                f.getName(),
                                                f.getImageUrl(),
                                                f.getSilhouetteImageUrl(),
                                                f.getEmotion().getName()))
                        .toList());
    }
}
