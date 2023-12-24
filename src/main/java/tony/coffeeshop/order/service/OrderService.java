package tony.coffeeshop.order.service;

import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;

public interface OrderService {

    OrderResponseDto orderMenu(OrderRequestDto orderRequestDto);
}
