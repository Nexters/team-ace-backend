package com.nexters.teamace.fairy.infrastructure;

import com.nexters.teamace.common.infrastructure.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "acquired_fairies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AcquiredFairyEntity extends BaseEntity {

    private Long fairyId;
    private Long userId;

    public AcquiredFairyEntity(final long id, final Long fairyId, final Long userId) {
        super(id);
        this.fairyId = fairyId;
        this.userId = userId;
    }

    public AcquiredFairyEntity(final Long fairyId, final Long userId) {
        this.fairyId = fairyId;
        this.userId = userId;
    }
}
