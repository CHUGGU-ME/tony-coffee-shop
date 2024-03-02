package tony.coffeeshop.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tony.coffeeshop.order.domain.dto.OrderRequestDto;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;
import tony.coffeeshop.order.domain.dto.OrderWeeklyTop3Dto;
import tony.coffeeshop.order.service.OrderService;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<OrderResponseDto> orderMenu(@RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.orderMenu(orderRequestDto));
    }

    @GetMapping("/weekly-top3")
    public ResponseEntity<List<OrderWeeklyTop3Dto>> getWeeklyTop3() {
        return ResponseEntity.ok(orderService.getWeeklyTop3());
    }
}
