package tony.coffeeshop.point.domain.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import tony.coffeeshop.point.domain.Point;
import tony.coffeeshop.user.domain.User;

@Getter
@Builder
public class PointDepositRequestDto {

    private Long userSeq;

    private int point;

    private TransactionType transactionType;

    private LocalDateTime transactionAt;

    public Point toEntity(User user) {
        return Point.builder()
                .user(user)
                .point(this.point)
                .transactionType(this.transactionType.name())
                .transactionAt(this.transactionAt)
                .build();
    }
}
