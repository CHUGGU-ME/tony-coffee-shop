package tony.coffeeshop.pointtransaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tony.coffeeshop.pointtransaction.component.PointTransactionComponent;
import tony.coffeeshop.pointtransaction.domain.dto.PointDepositRequestDto;

@Service
@RequiredArgsConstructor
public class PointTransactionServiceImpl implements PointTransactionService {

    private final PointTransactionComponent pointTransactionComponent;

    public static String USER_POINT_LOCK_PREFIX = "USER_";

    @Override
    public void depositPoint(PointDepositRequestDto pointDepositRequestDto) {
        pointTransactionComponent.depositPoint(pointDepositRequestDto);
    }
}
