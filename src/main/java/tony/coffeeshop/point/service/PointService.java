package tony.coffeeshop.point.service;

import tony.coffeeshop.point.domain.dto.PointDepositRequestDto;

public interface PointService {

    void depositPoint(PointDepositRequestDto pointDepositRequestDto);
}
