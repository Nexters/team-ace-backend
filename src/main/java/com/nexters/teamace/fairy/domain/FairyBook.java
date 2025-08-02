package com.nexters.teamace.fairy.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class FairyBook {

    private final List<FairyBookEntry> fairies;

    public FairyBook(List<FairyBookEntry> fairies) {
        this.fairies = fairies;
    }
}
