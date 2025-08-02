package com.nexters.teamace.fairy.domain;

import lombok.Getter;

@Getter
public class FairyBookEntry {
    private final Fairy fairy;
    private final boolean acquired;

    public FairyBookEntry(Fairy fairy, boolean acquired) {
        this.fairy = fairy;
        this.acquired = acquired;
    }
}
