package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/")
public class BoardController {

//    private final BoardService boardService;

//    @GetMapping("list/{categoryNo}/{page}") //게시판 목록 가져오기
//    public ResponseEntity<?> getFreeBoardList(@PathVariable int categoryNo, @PathVariable int page) {
//        switch (categoryNo) {
//            case 1:
//        }
//    }


//    @PostMapping("add")
//    public ResponseEntity<?> registBoard(@RequestBody BoardDetailDto boardDetailDto) {
//        long boardNo = boardService.register(boardDetailDto);
//        return ResponseEntity.ok(boardNo);
//    }

}
