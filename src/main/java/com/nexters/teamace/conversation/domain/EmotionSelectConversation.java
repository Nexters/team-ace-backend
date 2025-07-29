package com.nexters.teamace.conversation.domain;

import java.util.List;

public record EmotionSelectConversation(List<Emotions> emotions) {

    public record Emotions(String name, int score) {}
}
