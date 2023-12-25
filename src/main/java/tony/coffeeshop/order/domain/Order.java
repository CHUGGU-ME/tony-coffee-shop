package tony.coffeeshop.order.domain;

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
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import tony.coffeeshop.order.domain.dto.OrderResponseDto;
import tony.coffeeshop.user.domain.User;

@Table(name = "ORDERS")
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    @Comment("주문ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @Column(name = "MENU_NAME")
    @Comment("메뉴명")
    private String menuName;

    @Column(name = "ORDER_PRICE")
    @Comment("주문 가격")
    private int orderPrice;

    @Column(name = "ORDERED_AT")
    @Comment("주문 일시")
    private LocalDateTime orderedAt;

    public OrderResponseDto toDto() {
        return OrderResponseDto.builder()
                .userSeq(this.user.getId())
                .menuName(this.menuName)
                .orderPrice(this.orderPrice)
                .orderedAt(this.orderedAt)
                .build();
    }
}
