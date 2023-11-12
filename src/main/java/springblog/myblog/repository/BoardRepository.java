package springblog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springblog.myblog.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
