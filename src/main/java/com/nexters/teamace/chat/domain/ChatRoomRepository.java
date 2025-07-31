package com.nexters.teamace.chat.domain;

import java.util.Optional;

public interface ChatRoomRepository {
    ChatRoom save(ChatRoom chatRoom);

    Optional<ChatRoom> findById(Long id);
}
