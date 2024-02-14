package tony.coffeeshop.order.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
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
import tony.coffeeshop.menu.domain.Menu;
import tony.coffeeshop.menu.repository.MenuRepository;
import tony.coffeeshop.menu.service.MenuService;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.service.OrderService;
import tony.coffeeshop.user.domain.User;
import tony.coffeeshop.user.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class OrderControllerTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EntityManager em;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String urlPrefix;

    public OrderControllerTest() {
        this.urlPrefix = "/order";
    }

    @DisplayName("user orders menu")
    @Test
    void testUserOrder() throws Exception {
        // given
        User testUser = User.builder()
                .userId("testUser")
                .userPoint(50000)
                .build();

        User saveUser = userRepository.save(testUser);

        Menu iceAmericano = Menu.builder()
                .menuName("ice americano")
                .menuPrice(5000)
                .build();

        Menu hotChoco = Menu.builder()
                .menuName("hot chocolate")
                .menuPrice(4500)
                .build();

        Menu hotLatte = Menu.builder()
                .menuName("hotLatte")
                .menuPrice(5500)
                .build();

        menuRepository.saveAll(List.of(iceAmericano, hotChoco, hotLatte));

        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(hotChoco.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post(urlPrefix)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.registerModule(new JavaTimeModule())
                        .writeValueAsString(orderRequestDto))
        );

        resultActions.andExpect(status().isOk());

        // docs
        resultActions.andDo(document("User orders menu",
                "User orders menu",
                "User can order menu from cafe",
                false,
                false,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(userOrderRequestDtoFields),
                responseFields(userOrderResponseDtoFields)));
    }

    private final FieldDescriptor[] userOrderRequestDtoFields = {
            fieldWithPath("userSeq").description("User sequence number"),
            fieldWithPath("menuId").description("Menu id"),
            fieldWithPath("orderedAt").description("Ordered date and time"),
    };

    private final FieldDescriptor[] userOrderResponseDtoFields = {
            fieldWithPath("id").description("Order id"),
            fieldWithPath("userSeq").description("User sequence number"),
            fieldWithPath("menuName").description("Menu name"),
            fieldWithPath("orderPrice").description("Order price"),
            fieldWithPath("orderedAt").description("Ordered date and time"),
    };
}