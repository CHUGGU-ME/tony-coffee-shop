package tony.coffeeshop.menu.component;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tony.coffeeshop.menu.domain.Menu;
import tony.coffeeshop.menu.repository.MenuRepository;

@Component
@RequiredArgsConstructor
public class MenuComponent {

    private final MenuRepository menuRepository;

    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }
}
