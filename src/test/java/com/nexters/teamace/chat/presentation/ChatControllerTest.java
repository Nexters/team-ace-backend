package com.nexters.teamace.chat.presentation;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.Schema.schema;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.nexters.teamace.common.utils.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

class ChatControllerTest extends ControllerTest {

  @Test
  @DisplayName("채팅을 생성할 수 있다")
  void createChat() throws Exception {
    // given
    final ChatRequest request = new ChatRequest("user123");
    final String requestBody = objectMapper.writeValueAsString(request);
    final ChatResponse response = new ChatResponse(1L);

    // when
    final ResultActions resultActions =
        mockMvc.perform(
            post("/api/v1/chats").contentType(MediaType.APPLICATION_JSON).content(requestBody));

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.data", equalTo(asParsedJson(response))))
        .andExpect(jsonPath("$.error").doesNotExist())
        .andDo(
            MockMvcRestDocumentationWrapper.document(
                "{class_name}/{method_name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Chat")
                        .description("채팅 생성")
                        .requestFields(
                            fieldWithPath("userId")
                                .type(JsonFieldType.STRING)
                                .description("사용자 ID"))
                        .responseFields(
                            fieldWithPath("success")
                                .type(JsonFieldType.BOOLEAN)
                                .description("성공 여부"),
                            fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                            fieldWithPath("data.chatId")
                                .type(JsonFieldType.NUMBER)
                                .description("생성된 채팅 ID"),
                            fieldWithPath("error")
                                .type(JsonFieldType.NULL)
                                .description("에러 정보 (성공 시 null)"))
                        .requestSchema(schema("ChatRequest"))
                        .responseSchema(schema("ChatResponse"))
                        .build())));
  }
}
