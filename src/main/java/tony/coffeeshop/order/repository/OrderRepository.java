package tony.coffeeshop.order.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tony.coffeeshop.order.domain.Order;
import tony.coffeeshop.order.domain.dto.OrderWeeklyTop3Dto;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT new tony.coffeeshop.order.domain.dto.OrderWeeklyTop3Dto(COUNT(o.id), o.menuName)" +
            "FROM Order o " +
            "WHERE o.orderedAt BETWEEN :startDate AND :endDate " +
            "GROUP BY o.menuName " +
            "ORDER BY COUNT(o.id) DESC " +
            "LIMIT 3")
    List<OrderWeeklyTop3Dto> getWeeklyTop3(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
