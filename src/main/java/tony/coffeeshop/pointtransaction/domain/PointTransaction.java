package tony.coffeeshop.pointtransaction.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.web.bind.annotation.CookieValue;
import tony.coffeeshop.user.domain.User;

@Getter
@Table(name = "POINT_TRANSACTION")
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PointTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POINT_TRANSACTION_ID")
    @Comment("포인트 트랜잭션 ID")
    private Long id;

    @Comment("유저 시퀀스")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @Column(name = "POINT")
    @Comment("충전/사용 포인트")
    private int point;

    @Column(name = "TRANSACTION_TYPE")
    @Comment("포인트 이력 구분")
    private String transactionType;

    @Column(name = "TRANSACTION_AT")
    @Comment("포인트 이력 일시")
    private LocalDateTime transactionAt;
}
