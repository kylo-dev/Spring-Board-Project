package springblog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springblog.myblog.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // PrincipalDetailService에서 사용
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);
}
