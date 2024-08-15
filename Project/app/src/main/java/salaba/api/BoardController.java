package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salaba.dto.board.BoardLikeDto;
import salaba.dto.board.BoardCreateDto;
import salaba.dto.board.BoardDto;
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

//    @GetMapping("{id}")
//    public ResponseEntity<?> getBoard(@PathVariable Long id) {
//        boardService.
//    }


    @PostMapping("like")
    public ResponseEntity<?> likeBoard(@RequestBody BoardLikeDto boardLikeDto) {
        return ResponseEntity.ok(new IdResponse(boardService.likeBoard(boardLikeDto)));
    }

    @DeleteMapping("cancelLike")
    public ResponseEntity<?> cancelLikeBoard(@RequestBody BoardLikeDto boardLikeDto) {
        return ResponseEntity.ok(new IdResponse(boardService.cancelLikeBoard(boardLikeDto)));
    }

}
