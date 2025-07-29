package com.nexters.teamace.emotion.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emotion")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmotionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emotion_id")
    private Long id;

    private String name;

    private String description;

    public EmotionEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
