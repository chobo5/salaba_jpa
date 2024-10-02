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
import salaba.global.dto.IdResDto;
import salaba.domain.board.service.BoardService;
import salaba.domain.board.dto.request.ReplyCreateReqDto;
import salaba.domain.board.dto.request.ReplyModifyReqDto;
import salaba.domain.board.dto.request.ReplyToReplyCreateReqDto;
import salaba.domain.board.dto.response.ReplyByMemberResDto;
import salaba.domain.board.dto.response.ReplyModiResDto;
import salaba.domain.board.service.ReplyService;
import salaba.domain.auth.interceptor.MemberContextHolder;
import salaba.util.RestResult;

@Tag(name = "게시판 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/")
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;

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
        return RestResult.success(boardService.modify(MemberContextHolder.getMemberId(), boardModifyReqDto));
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

    @Operation(summary = "댓글 작성")
    @PostMapping("reply/new")
    public RestResult<?> createReply(@RequestBody ReplyCreateReqDto replyCreateReqDto) {
        Long replyId = replyService.createReply(MemberContextHolder.getMemberId(), replyCreateReqDto);
        return RestResult.success(new IdResDto(replyId));
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("reply/modify")
    public RestResult<?> modifyReply(@RequestBody ReplyModifyReqDto replyModifyReqDto) {
        ReplyModiResDto replyModi = replyService.modify(replyModifyReqDto, MemberContextHolder.getMemberId());
        return RestResult.success(replyModi);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("reply/delete")
    public RestResult<?> deleteReply(@RequestParam Long replyId) {
        Long deletedId = replyService.delete(replyId, MemberContextHolder.getMemberId());
        return RestResult.success(new IdResDto(deletedId));
    }

    @Operation(summary = "대댓글 작성")
    @PostMapping("reply/toReply/new")
    public RestResult<?> createReplyToReply(@RequestBody ReplyToReplyCreateReqDto replyCreateDto) {
        Long replyToReplyId = replyService.createReplyToReply(MemberContextHolder.getMemberId(), replyCreateDto);
        return RestResult.success(new IdResDto(replyToReplyId));
    }


    @Operation(summary = "회원이 작성한 댓글 목록")
    @GetMapping("reply/wrote/list")
    public RestResult<?> replyListByMember(@RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ReplyByMemberResDto> replies = replyService.getRepliesByMember(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(replies);
    }




}
