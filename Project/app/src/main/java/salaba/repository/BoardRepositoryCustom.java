package salaba.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import salaba.dto.board.BoardDto;
import salaba.entity.board.BoardCategory;

public interface BoardRepositoryCustom {
    Page<BoardDto> getList(BoardCategory category, Pageable pageable);
}
