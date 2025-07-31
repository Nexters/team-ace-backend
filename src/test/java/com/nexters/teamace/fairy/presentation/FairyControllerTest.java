package com.nexters.teamace.fairy.presentation;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.Schema.schema;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.nexters.teamace.common.annotation.WithMockCustomUser;
import com.nexters.teamace.common.presentation.UserInfo;
import com.nexters.teamace.common.utils.ControllerTest;
import com.nexters.teamace.fairy.application.FairyResult;
import com.nexters.teamace.fairy.application.dto.FairyInfo;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

class FairyControllerTest extends ControllerTest {

    @Test
    @DisplayName("인증된 사용자는 요정 추천을 받을 수 있다")
    @WithMockCustomUser
    void getFairy_success() throws Exception {
        // given
        long chatRoomId = 100L;

        given(authUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(new UserInfo(1L, "test-user", "테스트유저"));

        List<FairyInfo> fairyInfos =
                List.of(new FairyInfo(1L, "눈물방울 요정", "sad.png", "sad_sil.png", "슬픔"));
        FairyResult fairyResult = new FairyResult(fairyInfos);

        given(fairyService.getFairy(any(UserInfo.class), any(Long.class))).willReturn(fairyResult);

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        get("/api/v1/fairies").param("chatRoomId", String.valueOf(chatRoomId)));

        // then
        FairyResponse expectedResponse = new FairyResponse(fairyInfos);

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", equalTo(asParsedJson(expectedResponse))))
                .andExpect(jsonPath("$.error").doesNotExist())
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                "{class_name}/{method_name}",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("Fairy")
                                                .description("채팅방 기반 요정 추천 조회")
                                                .responseFields(
                                                        fieldWithPath("success")
                                                                .type(JsonFieldType.BOOLEAN)
                                                                .description("성공 여부"),
                                                        fieldWithPath("data.fairies")
                                                                .type(JsonFieldType.ARRAY)
                                                                .description("추천된 요정 목록"),
                                                        fieldWithPath("data.fairies[].id")
                                                                .type(JsonFieldType.NUMBER)
                                                                .description("요정 ID"),
                                                        fieldWithPath("data.fairies[].name")
                                                                .type(JsonFieldType.STRING)
                                                                .description("요정 이름"),
                                                        fieldWithPath("data.fairies[].image")
                                                                .type(JsonFieldType.STRING)
                                                                .description("요정 이미지 URL"),
                                                        fieldWithPath(
                                                                        "data.fairies[].silhouetteImage")
                                                                .type(JsonFieldType.STRING)
                                                                .description("요정 실루엣 이미지 URL"),
                                                        fieldWithPath("data.fairies[].emotion")
                                                                .type(JsonFieldType.STRING)
                                                                .description("관련 감정"),
                                                        fieldWithPath("error")
                                                                .type(JsonFieldType.NULL)
                                                                .description("에러 정보 (성공 시 null)"))
                                                .responseSchema(schema("FairyResponse"))
                                                .build())));
    }

    @Test
    @DisplayName("인증되지 않은 사용자는 401 Unauthorized 에러를 받는다")
    void getFairy_unauthorized() throws Exception {
        // given
        long chatRoomId = 100L;

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        get("/api/v1/fairies")
                                .param(
                                        "chatRoomId",
                                        String.valueOf(
                                                chatRoomId))); // @WithMockUser가 없으므로 인증되지 않은 상태로 요청

        // then
        resultActions.andExpect(status().isUnauthorized());
    }
}
