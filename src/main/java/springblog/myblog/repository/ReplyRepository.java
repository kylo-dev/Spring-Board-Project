package springblog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springblog.myblog.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
