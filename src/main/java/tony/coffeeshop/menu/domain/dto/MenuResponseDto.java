package tony.coffeeshop.menu.domain.dto;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class MenuResponseDto {
    private Long id;

    private String menuName;

    private int menuPrice;
}
