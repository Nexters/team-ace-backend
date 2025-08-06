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
import com.nexters.teamace.fairy.domain.Fairy;
import com.nexters.teamace.fairy.domain.FairyBook;
import com.nexters.teamace.fairy.domain.FairyBookEntry;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

class FairyBookControllerTest extends ControllerTest {

    @Test
    @DisplayName("인증된 사용자는 요정 도감을 조회할 수 있다")
    @WithMockCustomUser
    void getFairyBook_success() throws Exception {
        // given
        given(authUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(new UserInfo(1L, "test-user", "테스트유저"));

        FairyBook fairyBook =
                new FairyBook(
                        List.of(
                                new FairyBookEntry(
                                        new Fairy(1L, "눈물방울 요정", "sad.png", "sad_sil.png", 1L),
                                        true),
                                new FairyBookEntry(
                                        new Fairy(2L, "화르르 요정", "anger.png", "anger_sil.png", 2L),
                                        false),
                                new FairyBookEntry(
                                        new Fairy(
                                                3L,
                                                "질투쟁이 요정",
                                                "jealousy.png",
                                                "jealousy_sil.png",
                                                3L),
                                        true)));

        given(fairyService.getFairyBook(any(Long.class))).willReturn(fairyBook);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/fairy-books"));

        // then
        FairyBookResponse expectedResponse = FairyBookResponse.from(fairyBook.getFairies());

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
                                                .tag("FairyBook")
                                                .description("요정 도감 조회")
                                                .responseFields(
                                                        fieldWithPath("success")
                                                                .type(JsonFieldType.BOOLEAN)
                                                                .description("성공 여부"),
                                                        fieldWithPath("data.fairies")
                                                                .type(JsonFieldType.ARRAY)
                                                                .description("획득한 요정 목록"),
                                                        fieldWithPath("data.fairies[].id")
                                                                .type(JsonFieldType.NUMBER)
                                                                .description("요정 ID"),
                                                        fieldWithPath("data.fairies[].name")
                                                                .type(JsonFieldType.STRING)
                                                                .description("요정 이름"),
                                                        fieldWithPath("data.fairies[].imageUrl")
                                                                .type(JsonFieldType.STRING)
                                                                .description("요정 이미지 URL"),
                                                        fieldWithPath(
                                                                        "data.fairies[].silhouetteImageUrl")
                                                                .type(JsonFieldType.STRING)
                                                                .description("요정 실루엣 이미지 URL"),
                                                        fieldWithPath("data.fairies[].acquired")
                                                                .type(JsonFieldType.BOOLEAN)
                                                                .description("획득 여부"),
                                                        fieldWithPath("error")
                                                                .type(JsonFieldType.NULL)
                                                                .description("에러 정보 (성공 시 null)"))
                                                .responseSchema(schema("FairyBookResponse"))
                                                .build())));
    }

    @Test
    @DisplayName("인증되지 않은 사용자는 401 Unauthorized 에러를 받는다")
    void getFairyBook_unauthorized() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/fairy-books"));

        // then
        resultActions.andExpect(status().isUnauthorized());
    }
}
