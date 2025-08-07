package com.nexters.teamace.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.nexters.teamace.common.infrastructure.SlackAlertService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class E2ETest {

    @LocalServerPort protected int port;

    @Autowired protected ObjectMapper objectMapper;

    @MockitoBean protected SlackAlertService slackAlertService;

    protected final FixtureMonkey fixtureMonkey = FixtureMonkey.create();

    @BeforeEach
    void setUpRestAssured() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/api/v1";
    }
}
