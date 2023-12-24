package tony.coffeeshop.pointtransaction.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public int depositPoint(PointDepositRequestDto pointDepositRequestDto) {
        User user = userRepository.findById(pointDepositRequestDto.getUserSeq()).get();
        user.depositPoint(pointDepositRequestDto.getPoint());

        PointTransaction pointTransaction = pointDepositRequestDto.toEntity(user);
        pointTransactionRepository.save(pointTransaction);
        return user.getUserPoint();
    }
}
