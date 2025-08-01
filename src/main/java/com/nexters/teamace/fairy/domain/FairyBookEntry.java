package com.nexters.teamace.fairy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FairyBookEntry {
    private final Fairy fairy;
    private final boolean acquired;
}
