package com.nexters.teamace.fairy.presentation;

import com.nexters.teamace.common.presentation.ApiResponse;
import com.nexters.teamace.common.presentation.AuthUser;
import com.nexters.teamace.common.presentation.UserInfo;
import com.nexters.teamace.fairy.application.FairyService;
import com.nexters.teamace.fairy.domain.FairyBook;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fairy-books")
public class FairyBookController {

    private final FairyService fairyService;

    @GetMapping
    public ApiResponse<FairyBookResponse> getFairyBook(@AuthUser UserInfo user) {
        FairyBook fairyBook = fairyService.getFairyBook(user.userId());
        return ApiResponse.success(FairyBookResponse.from(fairyBook.getFairies()));
    }
}
