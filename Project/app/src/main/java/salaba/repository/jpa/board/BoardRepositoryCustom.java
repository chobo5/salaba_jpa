package salaba.repository.jpa.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import salaba.dto.request.board.BoardSearchReqDto;
import salaba.dto.response.BoardDetailResDto;
import salaba.dto.response.BoardResDto;
import salaba.entity.board.Board;

import java.util.Optional;

public interface BoardRepositoryCustom {
    Page<BoardResDto> getList(Pageable pageable);

    Optional<BoardDetailResDto> get(Long boardId);

    Page<BoardResDto> search(BoardSearchReqDto boardSearchReqDto, Pageable pageable);

    Optional<Board> findByIdWithWriter(Long boardId);
}
