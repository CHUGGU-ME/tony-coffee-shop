package tony.coffeeshop.menu.service;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tony.coffeeshop.menu.domain.Menu;
import tony.coffeeshop.menu.domain.dto.MenuResponseDto;
import tony.coffeeshop.menu.repository.MenuRepository;

@SpringBootTest
@Transactional
class MenuServiceImplTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("get all menu")
    @Test
    public void getAllMenu() {
        // given
        Menu iceAmericano = Menu.builder()
                .menuName("ice americano")
                .menuPrice(5000)
                .build();

        Menu hotChoco = Menu.builder()
                .menuName("hot chocolate")
                .menuPrice(4500)
                .build();

        Menu hotLatte = Menu.builder()
                .menuName("hotLatte")
                .menuPrice(5500)
                .build();

        menuRepository.saveAll(List.of(iceAmericano, hotChoco, hotLatte));

        // when
        List<MenuResponseDto> allMenu = menuService.getAllMenu();

        // then
        Assertions.assertThat(allMenu).hasSize(3);
        Assertions.assertThat(allMenu).extracting("menuName")
                .containsExactlyInAnyOrder(iceAmericano.getMenuName(), hotChoco.getMenuName(), hotLatte.getMenuName());
        Assertions.assertThat(allMenu).extracting("menuPrice")
                .containsExactlyInAnyOrder(iceAmericano.getMenuPrice(), hotChoco.getMenuPrice(), hotLatte.getMenuPrice());
    }
}