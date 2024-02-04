package tony.coffeeshop.order.service;

import java.util.List;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;
import tony.coffeeshop.order.domain.dto.OrderWeeklyTop3Dto;

public interface OrderService {

    OrderResponseDto orderMenu(OrderRequestDto orderRequestDto);

    List<OrderWeeklyTop3Dto> getWeeklyTop3();
}
