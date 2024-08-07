package salaba.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import salaba.dto.PageRequestDto;
import salaba.dto.PageResultDto;
import salaba.dto.board.BoardDetailDto;
import salaba.dto.board.BoardDto;
import salaba.entity.Board;
import salaba.entity.Comment;
import salaba.entity.Member;
import salaba.entity.Reply;
import salaba.repository.BoardRepository;
import salaba.service.BoardService;

import java.util.List;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDetailDto boardDetailDto) {
        log.info(boardDetailDto);
        Board board = dtoToEntity(boardDetailDto);
        boardRepository.save(board);
        return board.getBoardNo();
    }

    @Override
    public PageResultDto<BoardDto, Object[]> getBoardList(PageRequestDto requestDto, int categoryNo) {
        log.info(requestDto);
        Pageable pageable = requestDto.getPageable(Sort.by("createdDate").descending());
        Page<Object[]> result = boardRepository.getBoardList(categoryNo, pageable);

        Function<Object[], BoardDto> fn = (entity -> entityToDto((Board) entity[2], (Member) entity[3], (Long) entity[4], (Long) entity[5]));

        return new PageResultDto<>(result, fn);

    }

    @Override
    public BoardDetailDto getBoard(Long boardNo) {
        Object result = boardRepository.getBoard(boardNo);
        Object[] arr = (Object[]) result;
        return entityToDto((Board) arr[0], (List<Comment>) arr[1], (List<Reply>) arr[2]);
    }
}
