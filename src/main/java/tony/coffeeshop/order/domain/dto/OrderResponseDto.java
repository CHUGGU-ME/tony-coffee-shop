package tony.coffeeshop.order.domain.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import tony.coffeeshop.user.domain.User;

@Getter
@Builder
public class OrderResponseDto {

    private Long id;

    private Long userSeq;

    private String menuName;

    private int orderPrice;

    private LocalDateTime orderedAt;
}
