package tony.coffeeshop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tony.coffeeshop.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
