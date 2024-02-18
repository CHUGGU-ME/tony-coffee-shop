package tony.coffeeshop.point.component;

import static tony.coffeeshop.point.service.PointServiceImpl.USER_POINT_LOCK_PREFIX;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tony.coffeeshop.common.LockHandler;
import tony.coffeeshop.common.TransactionHandler;
import tony.coffeeshop.point.domain.Point;
import tony.coffeeshop.point.domain.dto.PointDepositRequestDto;
import tony.coffeeshop.point.repository.PointRepository;
import tony.coffeeshop.user.domain.User;
import tony.coffeeshop.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class PointComponent {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    private final LockHandler lockHandler;
    private final TransactionHandler transactionHandler;

    public void depositPoint(PointDepositRequestDto pointDepositRequestDto) {
        lockHandler.runOnLock(
                USER_POINT_LOCK_PREFIX + pointDepositRequestDto.getUserSeq(),
                2000L,
                1000L,
                () -> transactionHandler.runOnWriteTransaction(
                        () -> {

                            User user = userRepository.findById(pointDepositRequestDto.getUserSeq()).get();
                            user.depositPoint(pointDepositRequestDto.getPoint());

                            Point point = pointDepositRequestDto.toEntity(user);
                            pointRepository.save(point);
                            return null;
                        }));
    }
}
