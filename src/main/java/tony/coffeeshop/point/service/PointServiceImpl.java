package tony.coffeeshop.point.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tony.coffeeshop.point.component.PointComponent;
import tony.coffeeshop.point.domain.dto.PointDepositRequestDto;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointComponent pointComponent;

    public static String USER_POINT_LOCK_PREFIX = "USER_";

    @Override
    public void depositPoint(PointDepositRequestDto pointDepositRequestDto) {
        pointComponent.depositPoint(pointDepositRequestDto);
    }
}
