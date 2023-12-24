package tony.coffeeshop.pointtransaction.domain.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import tony.coffeeshop.pointtransaction.domain.PointTransaction;
import tony.coffeeshop.user.domain.User;

@Getter
@Builder
public class PointDepositRequestDto {

    private Long userSeq;

    private int point;

    private TransactionType transactionType;

    private LocalDateTime transactionAt;

    public PointTransaction toEntity(User user) {
        return PointTransaction.builder()
                .user(user)
                .point(this.point)
                .transactionType(this.transactionType.name())
                .transactionAt(this.transactionAt)
                .build();
    }
}
