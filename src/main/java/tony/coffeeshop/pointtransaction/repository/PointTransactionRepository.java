package tony.coffeeshop.pointtransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tony.coffeeshop.pointtransaction.domain.PointTransaction;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {

}
