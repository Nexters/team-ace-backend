package com.nexters.teamace.fairy.presentation;

import com.nexters.teamace.common.presentation.ApiResponse;
import com.nexters.teamace.common.presentation.AuthUser;
import com.nexters.teamace.common.presentation.UserInfo;
import com.nexters.teamace.fairy.application.FairyResult;
import com.nexters.teamace.fairy.application.FairyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fairies")
public class FairyController {

    private final FairyService fairyService;

    @GetMapping
    public ApiResponse<FairyResponse> getFairy(
            @AuthUser UserInfo user, @RequestParam Long chatRoomId) {
        FairyResult result = fairyService.getFairy(user, chatRoomId);
        return ApiResponse.success(new FairyResponse(result.fairies()));
    }
}
