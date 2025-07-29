package com.nexters.teamace.emotion.domain;

import java.util.List;

public interface EmotionRepository {
    List<Emotion> findAllByNamesIn(List<String> names);
}
