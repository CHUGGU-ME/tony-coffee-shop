package tony.coffeeshop.point.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import tony.coffeeshop.point.domain.dto.PointDepositRequestDto;
import tony.coffeeshop.point.domain.dto.TransactionType;
import tony.coffeeshop.point.service.PointService;
import tony.coffeeshop.user.domain.User;
import tony.coffeeshop.user.repository.UserRepository;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class PointControllerTest {

    @Autowired
    private PointService pointService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EntityManager em;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String urlPrefix;

    public PointControllerTest() {
        this.urlPrefix = "/point";
    }

    @DisplayName("user deposits point")
    @Test
    void testUserDepositPoint() throws Exception {
        // given
        User testUser = User.builder()
                .userId("testUser")
                .userPoint(5000)
                .build();

        User saveUser = userRepository.save(testUser);

        PointDepositRequestDto pointDepositRequestDto = PointDepositRequestDto.builder()
                .userSeq(saveUser.getId())
                .point(4550)
                .transactionType(TransactionType.DEPOSIT)
                .transactionAt(LocalDateTime.now())
                .build();


        // when
        ResultActions resultActions = mockMvc.perform(post(urlPrefix + "/deposit")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.registerModule(new JavaTimeModule())
                        .writeValueAsString(pointDepositRequestDto))
        );

        resultActions.andExpect(status().isOk());

        // docs
        resultActions.andDo(document("User deposit",
                "User deposit point",
                "User can deposit point",
                false,
                false,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(pointDepositRequestDtoFields)));
    }

    private final FieldDescriptor[] pointDepositRequestDtoFields = {
            fieldWithPath("userSeq").description("User sequence number"),
            fieldWithPath("point").description("point amount"),
            fieldWithPath("transactionType").description("transaction type"),
            fieldWithPath("transactionAt").description("transaction time"),
    };
}