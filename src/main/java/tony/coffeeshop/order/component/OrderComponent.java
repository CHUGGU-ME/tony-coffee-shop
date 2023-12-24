package tony.coffeeshop.order.component;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tony.coffeeshop.menu.domain.Menu;
import tony.coffeeshop.menu.repository.MenuRepository;
import tony.coffeeshop.order.domain.Order;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;
import tony.coffeeshop.order.repository.OrderRepository;
import tony.coffeeshop.user.domain.User;
import tony.coffeeshop.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class OrderComponent {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public OrderResponseDto orderMenu(OrderRequestDto orderRequestDto) {
        // 메뉴 찾아서 가격 조회하고
        Menu menu = menuRepository.findById(orderRequestDto.getMenuId()).get();

        // 유저 찾아서 포인트 충분한지 확인하고 메뉴 가격만큼 빼기
        User user = userRepository.findById(orderRequestDto.getUserSeq()).get();
        user.payPoint(menu.getMenuPrice());

        // 결제하고 order 만들어서 저장
        Order order = Order.builder()
                .user(user)
                .menuName(menu.getMenuName())
                .orderPrice(menu.getMenuPrice())
                .orderedAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);
        return order.toDto();
    }
}
