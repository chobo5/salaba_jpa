package salaba.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.board.BoardSearchReqDto;
import salaba.dto.request.board.BoardLikeReqDto;
import salaba.dto.request.board.BoardCreateReqDto;
import salaba.dto.response.BoardResDto;
import salaba.dto.request.board.BoardModifyReqDto;
import salaba.dto.response.IdResDto;
import salaba.service.BoardService;
import salaba.util.RestResult;

@Tag(name = "게시판 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시물 작성")
    @PostMapping("new")
    public RestResult<?> createBoard(@RequestBody BoardCreateReqDto boardCreateReqDto) {
        return RestResult.success(new IdResDto(boardService.createBoard(boardCreateReqDto)));

    }

    @Operation(summary = "게시물 목록")
    @GetMapping("list")
    public RestResult<?> getBoardList(Pageable pageable) {
        Page<BoardResDto> dtoList = boardService.list(pageable);
        return RestResult.success(dtoList);
    }

    @Operation(summary = "게시물 상세보기")
    @GetMapping("{id}")
    public RestResult<?> getBoard(@PathVariable Long id) {
        return RestResult.success(boardService.get(id));
    }


    @Operation(summary = "게시물 수정")
    @PutMapping("modify")
    public RestResult<?> modifyBoard(@RequestBody BoardModifyReqDto boardModifyReqDto) {
        return RestResult.success(boardService.modify(boardModifyReqDto));
    }

    @Operation(summary = "게시물 삭제")
    @DeleteMapping("delete/{id}")
    public RestResult<?> deleteBoard(@PathVariable Long id) {
        return RestResult.success(new IdResDto(boardService.delete(id)));
    }

    @Operation(summary = "게시물 검색")
    @GetMapping("search")
    public RestResult<?> searchBoard(BoardSearchReqDto boardSearchReqDto, Pageable pageable) {
        return RestResult.success(boardService.search(boardSearchReqDto, pageable));
    }

    @Operation(summary = "게시물 좋아요")
    @PostMapping("like")
    public RestResult<?> likeBoard(@RequestBody BoardLikeReqDto boardLikeReqDto) {
        return RestResult.success(new IdResDto(boardService.likeBoard(boardLikeReqDto)));
    }

    @Operation(summary = "게시물 좋아요 취소")
    @DeleteMapping("cancelLike")
    public RestResult<?> cancelLikeBoard(@RequestBody BoardLikeReqDto boardLikeReqDto) {
        return RestResult.success(new IdResDto(boardService.cancelLikeBoard(boardLikeReqDto)));
    }

}
