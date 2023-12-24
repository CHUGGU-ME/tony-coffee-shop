package tony.coffeeshop.pointtransaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tony.coffeeshop.pointtransaction.component.PointTransactionComponent;
import tony.coffeeshop.pointtransaction.domain.dto.PointDepositRequestDto;

@Service
@RequiredArgsConstructor
public class PointTransactionServiceImpl implements PointTransactionService {

    private final PointTransactionComponent pointTransactionComponent;

    @Override
    public int depositPoint(PointDepositRequestDto pointDepositRequestDto) {
        return pointTransactionComponent.depositPoint(pointDepositRequestDto);
    }
}
