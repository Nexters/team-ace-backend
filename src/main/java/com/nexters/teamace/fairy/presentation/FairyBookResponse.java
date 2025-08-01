package com.nexters.teamace.fairy.presentation;

import com.nexters.teamace.fairy.domain.FairyBookEntry;
import java.util.List;

public record FairyBookResponse(List<FairyBookEntryResponse> fairies) {

    public static FairyBookResponse from(List<FairyBookEntry> fairyBookEntries) {
        return new FairyBookResponse(
                fairyBookEntries.stream().map(FairyBookEntryResponse::from).toList());
    }

    private record FairyBookEntryResponse(
            Long id, String name, String imageUrl, String silhouetteImageUrl, boolean acquired) {

        public static FairyBookEntryResponse from(FairyBookEntry fairyBookEntry) {
            return new FairyBookEntryResponse(
                    fairyBookEntry.getFairy().getId(),
                    fairyBookEntry.getFairy().getName(),
                    fairyBookEntry.getFairy().getImageUrl(),
                    fairyBookEntry.getFairy().getSilhouetteImageUrl(),
                    fairyBookEntry.isAcquired());
        }
    }
}
