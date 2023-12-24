package tony.coffeeshop.user.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Table(name = "User")
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("사용자 시퀀스")
    @Column(name = "USER_SEQ")
    private Long id;

    @Comment("사용자 ID")
    private String userId;

    @Comment("사용자 포인트")
    private int userPoint;

    public void depositPoint(int point) {
        this.userPoint += point;
    }

    public void payPoint(int menuPrice) {
        if (this.getUserPoint() < menuPrice) {
            throw new IllegalStateException("user current point is not enough");
        }
        this.userPoint -= menuPrice;
    }
}
