package tony.coffeeshop.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tony.coffeeshop.menu.domain.dto.MenuResponseDto;
import tony.coffeeshop.menu.service.MenuService;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "get all menu")
    @GetMapping("/all-menu")
    public List<MenuResponseDto> getAllMenu() {
        return menuService.getAllMenu();
    }

}
