package tony.coffeeshop.order.domain.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Builder
public class OrderRequestDto {

    private Long userSeq;

    private Long menuId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderedAt;
}
