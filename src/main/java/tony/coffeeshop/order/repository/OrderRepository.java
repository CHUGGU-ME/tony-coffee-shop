package tony.coffeeshop.order.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tony.coffeeshop.order.domain.Order;
import tony.coffeeshop.order.domain.dto.OrderWeeklyTop3;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT COUNT(o.ORDER_ID) AS orderCount, o.MENU_NAME AS menuName"
            + " FROM ORDERS o"
            + " LEFT JOIN MENU m ON o.MENU_NAME = m.MENU_NAME"
            + " WHERE o.ORDERED_AT BETWEEN CURDATE() - INTERVAL 6 DAY"
            + " AND CURDATE() + INTERVAL 1 DAY"
            + " GROUP BY o.MENU_NAME"
            + " ORDER BY orderCount DESC"
            + " LIMIT 3;", nativeQuery = true)
    List<OrderWeeklyTop3> getWeeklyTop3();
}
