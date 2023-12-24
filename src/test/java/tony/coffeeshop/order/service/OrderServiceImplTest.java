package tony.coffeeshop.order.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tony.coffeeshop.menu.domain.Menu;
import tony.coffeeshop.menu.repository.MenuRepository;
import tony.coffeeshop.order.domain.Order;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;
import tony.coffeeshop.user.domain.User;
import tony.coffeeshop.user.repository.UserRepository;

@SpringBootTest
@Transactional
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("user orders menu by using its point")
    @Test
    public void userOrderMenu() {
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

        // when
        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(hotChoco.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        OrderResponseDto orderResponseDto = orderService.orderMenu(orderRequestDto);

        // then
        Assertions.assertThat(orderResponseDto.getUserSeq()).isEqualTo(saveUser.getId());
        Assertions.assertThat(orderResponseDto.getMenuName()).isEqualTo(hotChoco.getMenuName());
        Assertions.assertThat(orderResponseDto.getOrderPrice()).isEqualTo(hotChoco.getMenuPrice());
    }
}
