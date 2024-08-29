package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.BoardSearchReqDto;
import salaba.dto.request.board.BoardLikeDto;
import salaba.dto.request.board.BoardCreateDto;
import salaba.dto.request.board.BoardDto;
import salaba.dto.request.board.BoardModifyDto;
import salaba.entity.board.BoardCategory;
import salaba.dto.response.IdResDto;
import salaba.service.BoardService;
import salaba.util.RestResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("new")
    public RestResult<?> createBoard(@RequestBody BoardCreateDto boardCreateDto) {
        return RestResult.success(new IdResDto(boardService.createBoard(boardCreateDto)));
        
    }

    @GetMapping("list")
    public RestResult<?> getBoardList(BoardCategory category, Pageable pageable) {
        Page<BoardDto> dtoList = boardService.list(category, pageable);
        return RestResult.success(dtoList);
    }

    @GetMapping("{id}")
    public RestResult<?> getBoard(@PathVariable Long id) {
        return RestResult.success(boardService.get(id));
    }


    @PutMapping("modify")
    public RestResult<?> modifyBoard(@RequestBody BoardModifyDto boardModifyDto) {
        return RestResult.success(boardService.modify(boardModifyDto));
    }

    @DeleteMapping("delete/{id}")
    public RestResult<?> deleteBoard(@PathVariable Long id) {
        return RestResult.success(new IdResDto(boardService.delete(id)));
    }

    @GetMapping("search")
    public RestResult<?> searchBoard(BoardCategory category, BoardSearchReqDto boardSearchReqDto, Pageable pageable) {
        return RestResult.success(boardService.search(category, boardSearchReqDto, pageable));
    }

    @PostMapping("like")
    public RestResult<?> likeBoard(@RequestBody BoardLikeDto boardLikeDto) {
        return RestResult.success(new IdResDto(boardService.likeBoard(boardLikeDto)));
    }

    @DeleteMapping("cancelLike")
    public RestResult<?> cancelLikeBoard(@RequestBody BoardLikeDto boardLikeDto) {
        return RestResult.success(new IdResDto(boardService.cancelLikeBoard(boardLikeDto)));
    }

}
