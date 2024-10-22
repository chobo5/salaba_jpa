package salaba.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import salaba.domain.board.entity.Board;
import salaba.domain.board.entity.BoardLike;

import java.util.Optional;
import salaba.domain.member.entity.Member;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardAndMember(Board board, Member member);
}
