package tony.coffeeshop.order.service;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tony.coffeeshop.menu.domain.Menu;
import tony.coffeeshop.menu.domain.dto.MenuResponseDto;
import tony.coffeeshop.menu.repository.MenuRepository;
import tony.coffeeshop.menu.service.MenuService;
import tony.coffeeshop.order.domain.Order;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;
import tony.coffeeshop.order.domain.dto.OrderWeeklyTop3;
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

    @Autowired
    private MenuService menuService;

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

    @DisplayName("search weekly top 3 menus")
    @Test
    public void weeklyTop3() {
        // given
        User testUser = User.builder()
                .userId("testUser")
                .userPoint(100000)
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

        Menu iceWater = Menu.builder()
                .menuName("iceWater")
                .menuPrice(500)
                .build();

        Menu cheeseCake = Menu.builder()
                .menuName("chesseCake")
                .menuPrice(6000)
                .build();

        menuRepository.saveAll(List.of(iceAmericano, hotChoco, iceWater, hotLatte, cheeseCake));

        OrderRequestDto orderRequestDto1 = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(hotChoco.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        OrderRequestDto orderRequestDto2 = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(hotChoco.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        OrderRequestDto orderRequestDto3 = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(hotLatte.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        OrderRequestDto orderRequestDto4 = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(iceAmericano.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        OrderRequestDto orderRequestDto5 = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(iceWater.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        OrderRequestDto orderRequestDto6 = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(cheeseCake.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        OrderRequestDto orderRequestDto7 = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(iceAmericano.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        OrderRequestDto orderRequestDto8 = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(hotChoco.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        OrderRequestDto orderRequestDto9 = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(iceAmericano.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        OrderRequestDto orderRequestDto10 = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(iceWater.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        List<OrderRequestDto> orderRequestList = List.of(orderRequestDto1, orderRequestDto2,
                orderRequestDto3, orderRequestDto4, orderRequestDto5
                , orderRequestDto6, orderRequestDto7, orderRequestDto8, orderRequestDto9,
                orderRequestDto10);

        for (OrderRequestDto orderRequestDto : orderRequestList) {
            orderService.orderMenu(orderRequestDto);
        }

        // when
        List<OrderWeeklyTop3> weeklyTop3 = orderService.getWeeklyTop3();

        // then
        Assertions.assertThat(weeklyTop3).hasSize(3);
        Assertions.assertThat(weeklyTop3).extracting("menuName")
                .containsExactlyInAnyOrder(iceAmericano.getMenuName(), hotChoco.getMenuName(), iceWater.getMenuName());
    }
}
