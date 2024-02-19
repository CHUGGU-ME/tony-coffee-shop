package tony.coffeeshop.order.component;

import static tony.coffeeshop.point.service.PointServiceImpl.USER_POINT_LOCK_PREFIX;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tony.coffeeshop.common.LockHandler;
import tony.coffeeshop.common.TransactionHandler;
import tony.coffeeshop.menu.domain.Menu;
import tony.coffeeshop.menu.repository.MenuRepository;
import tony.coffeeshop.order.domain.Order;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;
import tony.coffeeshop.order.domain.dto.OrderWeeklyTop3Dto;
import tony.coffeeshop.order.repository.OrderRepository;
import tony.coffeeshop.point.domain.Point;
import tony.coffeeshop.point.domain.dto.TransactionType;
import tony.coffeeshop.point.repository.PointRepository;
import tony.coffeeshop.user.domain.User;
import tony.coffeeshop.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class OrderComponent {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final PointRepository pointTransactionRepository;
    private final LockHandler lockHandler;
    private final TransactionHandler transactionHandler;

    public OrderResponseDto orderMenu(OrderRequestDto orderRequestDto) {
        return lockHandler.runOnLock(
            USER_POINT_LOCK_PREFIX + orderRequestDto.getUserSeq(),
            2000L,
                1000L,
                () -> transactionHandler.runOnWriteTransaction(
                        () -> {
                            // 메뉴 찾아서 가격 조회하고
                            Menu menu = menuRepository.findById(orderRequestDto.getMenuId()).get();

                            // 유저 찾아서 포인트 충분한지 확인하고 메뉴 가격만큼 빼기
                            User user = userRepository.findById(orderRequestDto.getUserSeq()).get();
                            user.payPoint(menu.getMenuPrice());

                            LocalDateTime transactionAt = LocalDateTime.now();
                            // 포인트 사용 기록 저장
                            Point point = Point.builder()
                                    .user(user)
                                    .point(menu.getMenuPrice())
                                    .transactionType(TransactionType.USE.name())
                                    .transactionAt(transactionAt)
                                    .build();
                            pointTransactionRepository.save(point);

                            // 결제하고 order 만들어서 저장
                            Order order = Order.builder()
                                    .user(user)
                                    .menuName(menu.getMenuName())
                                    .orderPrice(menu.getMenuPrice())
                                    .orderedAt(transactionAt)
                                    .build();
                            orderRepository.save(order);
                            return order.toDto();
                        }
                )
        );
    }

    @Transactional(readOnly = true)
    public List<OrderWeeklyTop3Dto> getWeeklyTop3(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.getWeeklyTop3(startDate, endDate);
    }
}
