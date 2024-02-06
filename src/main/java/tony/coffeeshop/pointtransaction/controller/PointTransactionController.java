package tony.coffeeshop.pointtransaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tony.coffeeshop.pointtransaction.domain.dto.PointDepositRequestDto;
import tony.coffeeshop.pointtransaction.service.PointTransactionService;

@RestController
@RequiredArgsConstructor
public class PointTransactionController {

    private final PointTransactionService pointTransactionService;

    @Operation(summary = "user deposit point")
    @PostMapping("/point/deposit")
    public void getAllMenu(@RequestBody PointDepositRequestDto pointDepositRequestDto) {
        pointTransactionService.depositPoint(pointDepositRequestDto);
    }
}
