package com.nexters.teamace.common.presentation;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorMessage(
    String code, String message, Map<String, String> details, LocalDateTime timestamp) {

  public ErrorMessage(String code, String message) {
    this(code, message, null, LocalDateTime.now());
  }

  public ErrorMessage(String code, String message, Map<String, String> details) {
    this(code, message, details, LocalDateTime.now());
  }
}
