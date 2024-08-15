package salaba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.board.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
