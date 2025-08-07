package com.nexters.teamace.common.infrastructure;

import com.nexters.teamace.common.application.AlertService;
import com.nexters.teamace.common.exception.ErrorType;
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;
import io.jsonwebtoken.lang.Strings;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SlackAlertService implements AlertService {

    @Value("${slack.enabled:false}")
    private boolean enabled;

    @Value("${slack.webhook-url:}")
    private String webhookUrl;

    private final Slack slack = Slack.getInstance();

    @Async
    @Override
    public void error(ErrorType errorType, String message) {
        send("Error 발생: " + errorType.name(), message);
    }

    private void send(String title, String message) {
        send("*%s*\n%s".formatted(title, message));
    }

    private void send(String message) {
        if (!enabled || !Strings.hasText(webhookUrl)) {
            log.debug("Slack alert deactivate or webhook url is empty");
        }
        try {
            Payload payload = Payload.builder().text(message).build();
            WebhookResponse response = slack.send(webhookUrl, payload);
            if (response == null || response.getCode() != 200) {
                log.error(
                        "Slack webhook 비정상 응답: code={}, body={}",
                        Objects.isNull(response) ? null : response.getCode(),
                        Objects.isNull(response) ? null : response.getBody());
            }
        } catch (IOException e) {
            log.error("Slack message sending failed: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Slack message sending failed with unexpected error: {}", e.getMessage(), e);
        }
    }
}
