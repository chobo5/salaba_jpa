package salaba.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import salaba.entity.Board;
import salaba.repository.search.SearchBoardRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {

    @Query("SELECT bc, bh, bs, b, bw, c, cw, r, rw " +
            "FROM Board b " +
            "LEFT JOIN b.writer bw " +
            "LEFT JOIN b.boardCategory bc " +
            "LEFT JOIN b.boardHead bh " +
            "LEFT JOIN b.boardScope bs " +
            "LEFT JOIN Comment c ON c.board = b " +
            "LEFT JOIN c.writer cw " +
            "LEFT JOIN Reply r ON r.comment = c " +
            "LEFT JOIN r.writer rw " +
            "WHERE b.boardNo = :boardNo ")
    List<Object[]> getBoard(@Param("boardNo") Long boardNo);

    @Query(value = "SELECT bc, bh, b, w, count(c), count(bl) " +
            "FROM Board b " +
            "LEFT JOIN b.writer w " +
            "LEFT JOIN b.boardHead bh " +
            "LEFT JOIN b.boardCategory bc " +
            "LEFT JOIN Comment c ON c.board = b " +
            "LEFT JOIN BoardLike bl ON bl.board = b " +
            "GROUP BY b " +
            "HAVING b.boardCategory.categoryNo = :categoryNo " +
            "AND b.state = '0'")
    Page<Object[]> getBoardList(@Param("categoryNo") int categoryNo, Pageable pageable);


}
