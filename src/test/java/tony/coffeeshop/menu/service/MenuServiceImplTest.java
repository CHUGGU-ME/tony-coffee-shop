package tony.coffeeshop.menu.service;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tony.coffeeshop.menu.domain.Menu;
import tony.coffeeshop.menu.domain.dto.MenuResponseDto;
import tony.coffeeshop.menu.repository.MenuRepository;
import tony.coffeeshop.order.service.OrderService;

@SpringBootTest
@Transactional
class MenuServiceImplTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @PersistenceContext
    EntityManager em;

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

        em.flush();
        em.clear();

        // when
        List<MenuResponseDto> allMenu = menuService.getAllMenu();

        // then
        assertThat(allMenu).hasSize(3);
        assertThat(allMenu).extracting("menuName")
                .containsExactlyInAnyOrder(iceAmericano.getMenuName(), hotChoco.getMenuName(), hotLatte.getMenuName());
        assertThat(allMenu).extracting("menuPrice")
                .containsExactlyInAnyOrder(iceAmericano.getMenuPrice(), hotChoco.getMenuPrice(), hotLatte.getMenuPrice());
    }
}