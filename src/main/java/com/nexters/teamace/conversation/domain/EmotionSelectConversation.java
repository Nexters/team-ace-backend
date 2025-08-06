package com.nexters.teamace.conversation.domain;

import java.util.Comparator;
import java.util.List;

public record EmotionSelectConversation(List<Emotions> emotions) {

    public EmotionSelectConversation {
        emotions.sort(Comparator.comparingInt(Emotions::score).reversed());
    }

    public record Emotions(String name, int score) {}
}
