package tony.coffeeshop.order.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tony.coffeeshop.order.component.OrderComponent;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;
import tony.coffeeshop.order.domain.dto.OrderWeeklyTop3Dto;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderComponent orderComponent;

    @Override
    public OrderResponseDto orderMenu(OrderRequestDto orderRequestDto) {
        return orderComponent.orderMenu(orderRequestDto);
    }

    @Override
    public List<OrderWeeklyTop3Dto> getWeeklyTop3() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(6);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        List<OrderWeeklyTop3Dto> weeklyTop3 = orderComponent.getWeeklyTop3(startDate, endDate);
        return weeklyTop3;
    }
}
