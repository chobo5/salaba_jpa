package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salaba.dto.BoardSearchDto;
import salaba.dto.board.BoardLikeDto;
import salaba.dto.board.BoardCreateDto;
import salaba.dto.board.BoardDto;
import salaba.dto.board.BoardModifyDto;
import salaba.entity.board.BoardCategory;
import salaba.response.IdResponse;
import salaba.service.BoardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("new")
    public ResponseEntity<?> createBoard(@RequestBody BoardCreateDto boardCreateDto) {
        return ResponseEntity.ok(new IdResponse(boardService.createBoard(boardCreateDto)));
    }

    @GetMapping("list")
    public ResponseEntity<?> getBoardList(BoardCategory category, Pageable pageable) {
        Page<BoardDto> dtoList = boardService.list(category, pageable);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.get(id));
    }


    @PutMapping("modify")
    public ResponseEntity<?> modifyBoard(@RequestBody BoardModifyDto boardModifyDto) {
        return ResponseEntity.ok(boardService.modify(boardModifyDto));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id) {
        return ResponseEntity.ok(new IdResponse(boardService.delete(id)));
    }

    @GetMapping("search")
    public ResponseEntity<?> searchBoard(BoardCategory category, BoardSearchDto boardSearchDto, Pageable pageable) {
        return ResponseEntity.ok(boardService.search(category, boardSearchDto, pageable));
    }

    @PostMapping("like")
    public ResponseEntity<?> likeBoard(@RequestBody BoardLikeDto boardLikeDto) {
        return ResponseEntity.ok(new IdResponse(boardService.likeBoard(boardLikeDto)));
    }

    @DeleteMapping("cancelLike")
    public ResponseEntity<?> cancelLikeBoard(@RequestBody BoardLikeDto boardLikeDto) {
        return ResponseEntity.ok(new IdResponse(boardService.cancelLikeBoard(boardLikeDto)));
    }

}
