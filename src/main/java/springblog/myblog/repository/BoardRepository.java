package springblog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springblog.myblog.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
