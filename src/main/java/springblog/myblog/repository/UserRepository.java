package springblog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springblog.myblog.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // PrincipalDetailService에서 사용
    Optional<User> findByUsername(String username);
}
