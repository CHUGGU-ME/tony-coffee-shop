package tony.coffeeshop.point.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tony.coffeeshop.point.domain.dto.PointDepositRequestDto;
import tony.coffeeshop.point.service.PointService;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointTransactionService;

    @Operation(summary = "user deposit point")
    @PostMapping("/deposit")
    public void depositPoint(@RequestBody PointDepositRequestDto pointDepositRequestDto) {
        pointTransactionService.depositPoint(pointDepositRequestDto);
    }
}
