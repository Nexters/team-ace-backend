package com.nexters.teamace.fairy.infrastructure;

import com.nexters.teamace.emotion.domain.EmotionType;
import com.nexters.teamace.emotion.infrastructure.EmotionEntity;
import com.nexters.teamace.emotion.infrastructure.EmotionJpaRepository;
import com.nexters.teamace.fairy.domain.Fairy;
import com.nexters.teamace.fairy.domain.FairyRepository;
import com.nexters.teamace.fairy.infrastructure.dto.FairyProjection;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FairyRepositoryImpl implements FairyRepository {

    private final FairyJpaRepository fairyJpaRepository;
    private final EmotionJpaRepository emotionJpaRepository;
    private final FairyMapper fairyMapper;

    @Override
    public Fairy save(Fairy fairy) {
        EmotionEntity emotionEntity =
                emotionJpaRepository
                        .findById(fairy.getEmotionId())
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "Emotion not found: " + fairy.getEmotionId()));

        FairyEntity entity = fairyMapper.toEntity(fairy, emotionEntity);
        FairyEntity savedEntity = fairyJpaRepository.save(entity);
        return fairyMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Fairy> findById(Long id) {
        return fairyJpaRepository.findById(id).map(fairyMapper::toDomain);
    }

    @Override
    public List<FairyProjection> findAllByEmotionNames(List<EmotionType> emotionNames) {
        return fairyJpaRepository.findAllByEmotionNames(emotionNames);
    }

    @Override
    public List<Fairy> findAll() {
        return fairyJpaRepository.findAll().stream().map(fairyMapper::toDomain).toList();
    }
}
