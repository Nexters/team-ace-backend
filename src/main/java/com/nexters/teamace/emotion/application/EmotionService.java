package com.nexters.teamace.emotion.application;

import com.nexters.teamace.emotion.domain.Emotion;
import com.nexters.teamace.emotion.domain.EmotionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmotionService {
    private final EmotionRepository emotionRepository;

    public List<Emotion> findAllByNamesIn(List<String> names) {
        return emotionRepository.findAllByNamesIn(names);
    }
}
