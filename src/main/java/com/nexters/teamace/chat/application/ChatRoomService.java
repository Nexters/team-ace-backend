package com.nexters.teamace.chat.application;

import com.nexters.teamace.chat.domain.ChatContext;
import com.nexters.teamace.chat.domain.ChatMessageGenerator;
import com.nexters.teamace.chat.domain.ChatRoom;
import com.nexters.teamace.chat.domain.ChatRoomRepository;
import com.nexters.teamace.common.infrastructure.annotation.ReadOnlyTransactional;
import com.nexters.teamace.user.application.GetUserResult;
import com.nexters.teamace.user.application.UserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageGenerator chatMessageGenerator;
    private final UserService userService;

    @Transactional
    public CreateChatRoomResult createChat(final ChatRoomCommand command) {
        final GetUserResult user = userService.getUserByUsername(command.username());
        final ChatRoom chatRoom = ChatRoom.create(user.id());

        final String firstMessage = chatMessageGenerator.generateFirstMessage();
        chatRoom.addSystemMessage(firstMessage);
        final ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        return new CreateChatRoomResult(savedChatRoom.getId(), firstMessage);
    }

    @Transactional
    public SendMessageResult sendMessage(final SendMessageCommand command) {
        final ChatRoom chatRoom = chatRoomRepository.getById(command.chatRoomId());
        chatRoom.addUserMessage(command.message());

        final ChatContext chatContext = chatRoom.toChatContext();
        final String responseMessage =
                chatMessageGenerator.generateResponseMessage(command.message(), chatContext);

        chatRoom.addSystemMessage(responseMessage);
        chatRoomRepository.save(chatRoom);

        return new SendMessageResult(responseMessage);
    }

    @ReadOnlyTransactional
    public AllChatResult getAllChats(final AllChatQuery query) {
        final ChatRoom chatRoom = chatRoomRepository.getById(query.chatRoomId());
        if (!Objects.equals(chatRoom.getUserId(), query.userId())) {
            // 고민: 403을 명백하게 주는 게 좋을지 404로 hide 처리하는 것이 좋을지
            throw new AccessDeniedException(
                    "Not authorized to access this chat room. chat userId: %s but query userId: %s"
                            .formatted(chatRoom.getUserId(), query.userId()));
        }
        return new AllChatResult(
                chatRoom.getId(),
                chatRoom.getChats().stream()
                        .map(
                                chat ->
                                        new AllChatResult.ChatResult(
                                                chat.getId(), chat.getType(), chat.getMessage()))
                        .toList());
    }
}
