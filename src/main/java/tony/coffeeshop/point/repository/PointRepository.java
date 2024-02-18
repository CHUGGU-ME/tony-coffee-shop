package tony.coffeeshop.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tony.coffeeshop.point.domain.Point;

public interface PointRepository extends JpaRepository<Point, Long> {

}
