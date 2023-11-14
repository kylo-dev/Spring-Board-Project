package springblog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springblog.myblog.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b join fetch b.user where b.id =:id")
    Optional<Board> findBoardWithUser(@Param("id") Long id);

    @Query("select b from Board b join fetch b.user")
    List<Board> findAllBoardWithUser();
}
