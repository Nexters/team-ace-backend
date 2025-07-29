package com.nexters.teamace.fairy.infrastructure;

import com.nexters.teamace.fairy.domain.Fairy;
import com.nexters.teamace.fairy.domain.FairyRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FairyRepositoryImpl implements FairyRepository {

    private final FairyJpaRepository fairyJpaRepository;
    private final FairyMapper fairyMapper;

    @Override
    public Fairy save(Fairy fairy) {
        FairyEntity entity = fairyMapper.toEntity(fairy);
        FairyEntity savedEntity = fairyJpaRepository.save(entity);
        return fairyMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Fairy> findById(Long id) {
        return fairyJpaRepository.findById(id).map(fairyMapper::toDomain);
    }

    @Override
    public List<Fairy> findAllByEmotionIdIn(List<Long> emotionIds) {
        return fairyJpaRepository.findAllByEmotionIdIn(emotionIds).stream()
                .map(fairyMapper::toDomain)
                .toList();
    }
}
