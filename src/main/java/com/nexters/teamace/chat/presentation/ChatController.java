package com.nexters.teamace.chat.presentation;

import com.nexters.teamace.common.presentation.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
public class ChatController {

  @PostMapping
  public ApiResponse<ChatResponse> createChat(@RequestBody @Valid final ChatRequest request) {
    return ApiResponse.success(new ChatResponse(1L));
  }
}
