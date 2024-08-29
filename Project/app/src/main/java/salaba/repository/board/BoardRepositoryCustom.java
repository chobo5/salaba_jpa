package salaba.repository.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import salaba.dto.request.BoardSearchReqDto;
import salaba.dto.request.board.BoardDetailDto;
import salaba.dto.request.board.BoardDto;
import salaba.entity.board.Board;
import salaba.entity.board.BoardCategory;

import java.util.Optional;

public interface BoardRepositoryCustom {
    Page<BoardDto> getList(BoardCategory category, Pageable pageable);

    Optional<BoardDetailDto> get(Long boardId);

    Page<BoardDto> search(BoardCategory boardCategory, BoardSearchReqDto boardSearchReqDto, Pageable pageable);

    Optional<Board> findByIdWithWriter(Long boardId);
}
