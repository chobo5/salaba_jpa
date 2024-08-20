package salaba.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import salaba.entity.board.BoardLike;
import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    @Query("select bl from BoardLike bl where bl.member.id = :memberId and bl.board.id = :boardId")
    Optional<BoardLike> findByBoardIdAndMemberId(@Param("boardId") Long boardId, @Param("memberId") Long memberId);
}
