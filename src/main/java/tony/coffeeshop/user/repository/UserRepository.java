package tony.coffeeshop.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tony.coffeeshop.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
