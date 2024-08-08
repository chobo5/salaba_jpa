package salaba.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import salaba.entity.board.Board;

public interface SearchBoardRepository {
    Board search1(int categoryNo);

    Board search2();

    Page<Object[]> searchBoard(String type, String keyword, Pageable pageable);
}
