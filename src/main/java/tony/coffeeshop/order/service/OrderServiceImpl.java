package tony.coffeeshop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tony.coffeeshop.order.component.OrderComponent;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderComponent orderComponent;

    @Override
    public OrderResponseDto orderMenu(OrderRequestDto orderRequestDto) {
        return orderComponent.orderMenu(orderRequestDto);
    }
}
