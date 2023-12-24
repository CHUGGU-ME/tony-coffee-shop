package tony.coffeeshop.order.domain.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderRequestDto {

    private Long userSeq;

    private Long menuId;

    private LocalDateTime orderedAt;
}
