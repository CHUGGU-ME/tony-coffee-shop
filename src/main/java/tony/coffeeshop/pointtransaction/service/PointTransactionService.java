package tony.coffeeshop.pointtransaction.service;

import tony.coffeeshop.pointtransaction.domain.dto.PointDepositRequestDto;

public interface PointTransactionService {

    void depositPoint(PointDepositRequestDto pointDepositRequestDto);
}
