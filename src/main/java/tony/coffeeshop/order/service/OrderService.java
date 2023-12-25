package tony.coffeeshop.order.service;

import java.util.List;
import tony.coffeeshop.menu.domain.dto.MenuResponseDto;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;
import tony.coffeeshop.order.domain.dto.OrderWeeklyTop3;

public interface OrderService {

    OrderResponseDto orderMenu(OrderRequestDto orderRequestDto);

    List<OrderWeeklyTop3> getWeeklyTop3();
}
