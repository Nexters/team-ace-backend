package com.nexters.teamace.emotion.infrastructure;

import com.nexters.teamace.common.infrastructure.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emotions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmotionEntity extends BaseEntity {

    private String name;

    private String description;

    @Builder
    public EmotionEntity(long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }
}
