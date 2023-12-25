package tony.coffeeshop.order.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tony.coffeeshop.menu.domain.Menu;
import tony.coffeeshop.menu.domain.dto.MenuResponseDto;
import tony.coffeeshop.order.component.OrderComponent;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;
import tony.coffeeshop.order.domain.dto.OrderWeeklyTop3;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderComponent orderComponent;

    @Override
    public OrderResponseDto orderMenu(OrderRequestDto orderRequestDto) {
        return orderComponent.orderMenu(orderRequestDto);
    }

    @Override
    public List<OrderWeeklyTop3> getWeeklyTop3() {
        List<OrderWeeklyTop3> weeklyTop3 = orderComponent.getWeeklyTop3();
        return weeklyTop3;
    }
}
