package com.nexters.teamace.emotion.infrastructure;

import com.nexters.teamace.common.infrastructure.entity.BaseEntity;
import com.nexters.teamace.emotion.domain.EmotionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emotions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmotionEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 255)
    private EmotionType name;

    private String description;

    @Builder
    public EmotionEntity(long id, EmotionType name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }
}
