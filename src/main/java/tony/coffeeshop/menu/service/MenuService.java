package tony.coffeeshop.menu.service;

import java.util.List;
import tony.coffeeshop.menu.domain.dto.MenuResponseDto;

public interface MenuService {

    List<MenuResponseDto> getAllMenu();
}
