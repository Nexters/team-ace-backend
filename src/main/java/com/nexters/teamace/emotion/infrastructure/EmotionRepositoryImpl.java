package com.nexters.teamace.emotion.infrastructure;

import com.nexters.teamace.emotion.domain.Emotion;
import com.nexters.teamace.emotion.domain.EmotionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmotionRepositoryImpl implements EmotionRepository {

    private final EmotionJpaRepository emotionJpaRepository;

    @Override
    public List<Emotion> findAllByNamesIn(List<String> names) {
        return emotionJpaRepository.findAllByNameIn(names).stream()
                .map(
                        entity ->
                                new Emotion(
                                        entity.getId(), entity.getName(), entity.getDescription()))
                .toList();
    }
}
