package salaba.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import salaba.dto.BoardSearchDto;
import salaba.dto.board.BoardDetailDto;
import salaba.dto.board.BoardDto;
import salaba.entity.board.Board;
import salaba.entity.board.BoardCategory;

public interface BoardRepositoryCustom {
    Page<BoardDto> getList(BoardCategory category, Pageable pageable);

    BoardDetailDto get(Long boardId);

    Page<BoardDto> search(BoardCategory boardCategory, BoardSearchDto boardSearchDto, Pageable pageable);
}
