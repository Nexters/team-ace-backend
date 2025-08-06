package com.nexters.teamace.emotion.domain;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmotionType {
    ANGER("분노"),
    SADNESS("슬픔"),
    JEALOUSY("질투"),
    PAIN("아픔"),
    LONELINESS("외로움"),
    CONFUSION("혼란스러움"),
    HATRED("혐오"),
    FEAR("두려움"),
    LETHARGY("무기력"),
    SOLITUDE("고독"),
    SHAME("부끄러움");

    private final String description;
    private static final List<String> EMOTION_NAMES =
            Arrays.stream(values()).map(EmotionType::name).toList();

    public static List<String> getEmotionNames() {
        return EMOTION_NAMES;
    }
}
