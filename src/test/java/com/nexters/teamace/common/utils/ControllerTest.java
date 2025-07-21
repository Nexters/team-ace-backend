package com.nexters.teamace.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.nexters.teamace.auth.application.AuthService;
import com.nexters.teamace.auth.application.TokenService;
import com.nexters.teamace.auth.config.SecurityConfig;
import com.nexters.teamace.auth.infrastructure.security.JwtAuthenticationFilter;
import com.nexters.teamace.auth.infrastructure.security.SecurityErrorHandler;
import com.nexters.teamace.chat.application.ChatRoomService;
import com.nexters.teamace.common.presentation.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@AutoConfigureRestDocs
@Import({
    SecurityConfig.class,
    JwtAuthenticationFilter.class,
    SecurityErrorHandler.class,
    GlobalExceptionHandler.class
})
public abstract class ControllerTest {

    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;

    @MockitoBean protected ChatRoomService chatRoomService;
    @MockitoBean protected TokenService tokenService;
    @MockitoBean protected AuthService authService;

    protected Object asParsedJson(Object obj) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(obj);
        return JsonPath.read(json, "$");
    }
}
