package salaba.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import salaba.domain.board.dto.request.BoardSearchReqDto;
import salaba.domain.board.dto.response.BoardDetailResDto;
import salaba.domain.board.dto.response.BoardResDto;
import salaba.domain.board.entity.Board;

import java.util.Optional;

public interface BoardRepositoryCustom {
    Page<BoardResDto> getList(Pageable pageable);

    Optional<BoardDetailResDto> get(Long boardId);

    Page<BoardResDto> search(BoardSearchReqDto boardSearchReqDto, Pageable pageable);

    Optional<Board> findByIdWithWriter(Long boardId);
}
