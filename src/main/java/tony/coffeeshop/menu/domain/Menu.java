package tony.coffeeshop.menu.domain;

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
import tony.coffeeshop.menu.domain.dto.MenuResponseDto;

@Getter
@Table(name = "MENU")
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID")
    @Comment("메뉴ID")
    private Long id;

    @Column(name = "MENU_NAME")
    @Comment("메뉴명")
    private String menuName;

    @Column(name = "MENU_PRICE")
    @Comment("메뉴가격")
    private int menuPrice;

    public static MenuResponseDto toDto(Menu menu) {
        return MenuResponseDto.builder()
                .id(menu.getId())
                .menuName(menu.menuName)
                .menuPrice(menu.menuPrice)
                .build();
    }
}
