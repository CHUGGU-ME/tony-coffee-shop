package tony.coffeeshop.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;
import tony.coffeeshop.order.domain.dto.OrderWeeklyTop3Dto;
import tony.coffeeshop.order.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "user orders menu")
    @PostMapping("/order")
    public OrderResponseDto getAllMenu(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.orderMenu(orderRequestDto);
    }

    @Operation(summary = "get weekly ordered top 3 menus")
    @GetMapping("/order/weekly-top3")
    public List<OrderWeeklyTop3Dto> getWeeklyTop3() {
        return orderService.getWeeklyTop3();
    }
}
