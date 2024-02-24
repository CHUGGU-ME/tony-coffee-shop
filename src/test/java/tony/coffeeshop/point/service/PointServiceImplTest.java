package tony.coffeeshop.point.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tony.coffeeshop.point.domain.dto.PointDepositRequestDto;
import tony.coffeeshop.point.domain.dto.TransactionType;
import tony.coffeeshop.user.domain.User;
import tony.coffeeshop.user.repository.UserRepository;

@SpringBootTest
@Transactional
class PointServiceImplTest {

    @Autowired
    private PointService pointService;

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
        pointService.depositPoint(pointDepositRequestDto);

        // then
        User findUser = userRepository.findById(saveUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("Can't find user from user_seq")
        );
        assertThat(findUser.getUserPoint()).isEqualTo(9550);
    }
}