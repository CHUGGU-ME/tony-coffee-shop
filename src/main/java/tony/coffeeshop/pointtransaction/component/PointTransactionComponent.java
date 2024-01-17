package tony.coffeeshop.pointtransaction.component;

import static tony.coffeeshop.pointtransaction.service.PointTransactionServiceImpl.USER_POINT_LOCK_PREFIX;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tony.coffeeshop.common.LockHandler;
import tony.coffeeshop.common.TransactionHandler;
import tony.coffeeshop.pointtransaction.domain.PointTransaction;
import tony.coffeeshop.pointtransaction.domain.dto.PointDepositRequestDto;
import tony.coffeeshop.pointtransaction.repository.PointTransactionRepository;
import tony.coffeeshop.user.domain.User;
import tony.coffeeshop.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class PointTransactionComponent {

    private final PointTransactionRepository pointTransactionRepository;
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

                            PointTransaction pointTransaction = pointDepositRequestDto.toEntity(user);
                            pointTransactionRepository.save(pointTransaction);
                            return null;
                        }));
    }
}
