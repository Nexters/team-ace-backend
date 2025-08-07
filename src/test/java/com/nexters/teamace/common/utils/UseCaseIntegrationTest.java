package com.nexters.teamace.common.utils;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.nexters.teamace.chat.domain.ChatMessageGenerator;
import com.nexters.teamace.common.application.DateTimeHolder;
import com.nexters.teamace.common.application.SystemHolder;
import com.nexters.teamace.common.infrastructure.SlackAlertService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class UseCaseIntegrationTest {

    @MockitoBean protected SystemHolder systemHolder;
    @MockitoBean protected DateTimeHolder dateTimeHolder;
    @MockitoBean protected ChatMessageGenerator chatMessageGenerator;
    @MockitoBean protected SlackAlertService slackAlertService;

    protected final FixtureMonkey fixtureMonkey = FixtureMonkey.create();
}
