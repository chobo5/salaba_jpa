package salaba.domain.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.domain.board.dto.request.BoardSearchReqDto;
import salaba.domain.board.dto.request.BoardLikeReqDto;
import salaba.domain.board.dto.request.BoardCreateReqDto;
import salaba.domain.board.dto.response.BoardByMemberResDto;
import salaba.domain.board.dto.response.BoardResDto;
import salaba.domain.board.dto.request.BoardModifyReqDto;
import salaba.domain.common.dto.IdResDto;
import salaba.domain.board.service.BoardService;
import salaba.interceptor.MemberContextHolder;
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
        Long boardId = boardService.createBoard(MemberContextHolder.getMemberId(), boardCreateReqDto);
        return RestResult.success(new IdResDto(boardId));

    }

    @Operation(summary = "게시물 목록")
    @GetMapping("list")
    public RestResult<?> getBoardList(@RequestParam(defaultValue = "0") int pageNumber,
                                      @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<BoardResDto> dtoList = boardService.getBoardList(pageable);
        return RestResult.success(dtoList);
    }

    @Operation(summary = "게시물 상세보기")
    @GetMapping()
    public RestResult<?> getBoard(@RequestParam Long boardId) {
        return RestResult.success(boardService.getBoard(boardId));
    }


    @Operation(summary = "게시물 수정")
    @PutMapping("modify")
    public RestResult<?> modifyBoard(@RequestBody BoardModifyReqDto boardModifyReqDto) {
        return RestResult.success(boardService.modify(boardModifyReqDto));
    }

    @Operation(summary = "게시물 삭제")
    @DeleteMapping("delete")
    public RestResult<?> deleteBoard(@RequestParam Long boardId) {
        return RestResult.success(new IdResDto(boardService.delete(boardId)));
    }

    @Operation(summary = "게시물 검색")
    @GetMapping("search")
    public RestResult<?> searchBoard(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) String writer,
                                     @RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "10") int pageSize) {

        if ((title == null && writer == null) || (title != null && writer != null)) {
            throw new IllegalArgumentException("title과 writer 중 하나만 값이 있어야 합니다.");
        }

        BoardSearchReqDto reqDto = new BoardSearchReqDto(title, writer);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return RestResult.success(boardService.search(reqDto, pageable));
    }

    @Operation(summary = "게시물 좋아요")
    @PostMapping("like")
    public RestResult<?> likeBoard(@RequestBody BoardLikeReqDto boardLikeReqDto) {
        return RestResult.success(new IdResDto(boardService.likeBoard(MemberContextHolder.getMemberId(), boardLikeReqDto)));
    }

    @Operation(summary = "게시물 좋아요 취소")
    @DeleteMapping("cancelLike")
    public RestResult<?> cancelLikeBoard(@RequestBody BoardLikeReqDto boardLikeReqDto) {
        return RestResult.success(new IdResDto(boardService.cancelLikeBoard(MemberContextHolder.getMemberId(), boardLikeReqDto)));
    }

    @Operation(summary = "회원이 작성한 게시물 목록")
    @GetMapping("wrote/list")
    public RestResult<?> boardListByMember(@RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<BoardByMemberResDto> boards = boardService.boardsByMember(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(boards);
    }




}
