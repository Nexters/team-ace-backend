package com.nexters.teamace.fairy.infrastructure;

import com.nexters.teamace.emotion.domain.EmotionName;
import com.nexters.teamace.fairy.infrastructure.dto.FairyProjection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FairyJpaRepository extends JpaRepository<FairyEntity, Long> {

    @Query(
            "SELECT new com.nexters.teamace.fairy.infrastructure.dto.FairyProjection(f.id, f.name, f.imageUrl, f.silhouetteImageUrl, e.name, e.description) "
                    + "FROM FairyEntity f JOIN f.emotion e "
                    + "WHERE e.name IN :emotionNames")
    List<FairyProjection> findAllByEmotionNames(
            @Param("emotionNames") List<EmotionName> emotionNames);
}
