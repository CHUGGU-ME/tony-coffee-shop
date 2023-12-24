package tony.coffeeshop.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tony.coffeeshop.menu.domain.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
