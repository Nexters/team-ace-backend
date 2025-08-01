package com.nexters.teamace.fairy.domain;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class FairyBook {

    List<FairyBookEntry> fairies;

    public FairyBook(List<FairyBookEntry> fairies) {
        this.fairies = fairies;
    }
}
