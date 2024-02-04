package tony.coffeeshop.order.service;

import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tony.coffeeshop.menu.domain.Menu;
import tony.coffeeshop.menu.repository.MenuRepository;
import tony.coffeeshop.menu.service.MenuService;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;
import tony.coffeeshop.order.domain.dto.OrderWeeklyTop3Dto;
import tony.coffeeshop.user.domain.User;
import tony.coffeeshop.user.repository.UserRepository;

@SpringBootTest
@Slf4j
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuService menuService;

    @Autowired
    EntityManager em;

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

    @DisplayName("user orders menu concurrently")
    @Test
    public void userOrderMenuConcurrently() throws InterruptedException {
        // given
        User testUser = User.builder()
                .userId("testUser")
                .userPoint(10000)
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
        OrderRequestDto orderRequestDto1 = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(hotChoco.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        OrderRequestDto orderRequestDto2 = OrderRequestDto.builder()
                .userSeq(saveUser.getId())
                .menuId(hotLatte.getId())
                .orderedAt(LocalDateTime.now())
                .build();

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> orderService.orderMenu(orderRequestDto1)),
                CompletableFuture.runAsync(() -> orderService.orderMenu(orderRequestDto2))
        ).join();


        /*
        List<OrderRequestDto> orders = List.of(orderRequestDto1, orderRequestDto2);
        int numberThreads = 2;
        CountDownLatch countDownLatch = new CountDownLatch(numberThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numberThreads);

        for (int i = 0; i < numberThreads; i++) {
            int idx = i;
            try {
                executorService.execute(
                        () -> orderService.orderMenu(orders.get(idx))
                );
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
        countDownLatch.await();
        executorService.shutdown();
        */

        // then
        User user = userRepository.findById(orderRequestDto1.getUserSeq()).get();
        int userPoint = user.getUserPoint();
        Assertions.assertThat(10000 - 5500 - 5500).isEqualTo(userPoint);
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
        List<OrderWeeklyTop3Dto> weeklyTop3 = orderService.getWeeklyTop3();

        // then
        Assertions.assertThat(weeklyTop3).hasSize(3);
        Assertions.assertThat(weeklyTop3).extracting("menuName")
                .containsExactlyInAnyOrder(iceAmericano.getMenuName(), hotChoco.getMenuName(), iceWater.getMenuName());
    }
}
