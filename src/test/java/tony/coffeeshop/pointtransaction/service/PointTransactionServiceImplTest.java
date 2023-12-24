package tony.coffeeshop.pointtransaction.service;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tony.coffeeshop.pointtransaction.domain.PointTransaction;
import tony.coffeeshop.pointtransaction.domain.dto.PointDepositRequestDto;
import tony.coffeeshop.pointtransaction.domain.dto.TransactionType;
import tony.coffeeshop.pointtransaction.repository.PointTransactionRepository;
import tony.coffeeshop.user.domain.User;
import tony.coffeeshop.user.repository.UserRepository;

@SpringBootTest
@Transactional
class PointTransactionServiceImplTest {

    @Autowired
    private PointTransactionService pointTransactionService;

    @Autowired
    private PointTransactionRepository pointTransactionRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("user deposits point")
    @Test
    public void userDepositPoint() {
        // given
        User testUser = User.builder()
                .userId("testUser")
                .userPoint(5000)
                .build();

        User saveUser = userRepository.save(testUser);

        PointDepositRequestDto pointDepositRequestDto = PointDepositRequestDto.builder()
                .userSeq(saveUser.getId())
                .point(4550)
                .transactionType(TransactionType.DEPOSIT)
                .transactionAt(LocalDateTime.now())
                .build();

        // when
        pointTransactionService.depositPoint(pointDepositRequestDto);

        // then
        User findUser = userRepository.findById(saveUser.getId()).get();
        Assertions.assertThat(findUser.getUserPoint()).isEqualTo(9500);
    }
}