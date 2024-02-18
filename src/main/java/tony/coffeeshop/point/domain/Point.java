package tony.coffeeshop.point.domain;


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
import tony.coffeeshop.user.domain.User;

@Getter
@Table(name = "POINT")
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POINTID")
    @Comment("포인트 ID")
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
