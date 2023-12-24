package tony.coffeeshop.menu.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tony.coffeeshop.menu.component.MenuComponent;
import tony.coffeeshop.menu.domain.Menu;
import tony.coffeeshop.menu.domain.dto.MenuResponseDto;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{

    private final MenuComponent menuComponent;

    @Override
    public List<MenuResponseDto> getAllMenu() {
        List<Menu> allMenu = menuComponent.getAllMenu();
        return allMenu.stream().map(Menu::toDto).collect(Collectors.toList());
    }
}
