package tony.coffeeshop.pointtransaction.service;

import tony.coffeeshop.pointtransaction.domain.dto.PointDepositRequestDto;

public interface PointTransactionService {

    int depositPoint(PointDepositRequestDto pointDepositRequestDto);
}
