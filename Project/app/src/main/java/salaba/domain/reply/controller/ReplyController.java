package salaba.domain.reply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.domain.reply.dto.response.ReplyByMemberResDto;
import salaba.domain.reply.service.ReplyService;
import salaba.domain.reply.dto.request.ReplyCreateReqDto;
import salaba.domain.reply.dto.request.ReplyModifyReqDto;
import salaba.domain.reply.dto.request.ReplyToReplyCreateReqDto;
import salaba.domain.common.dto.IdResDto;
import salaba.domain.reply.dto.response.ReplyModiResDto;
import salaba.interceptor.MemberContextHolder;
import salaba.util.RestResult;

@Tag(name = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reply/")
public class ReplyController {
    private final ReplyService replyService;

    @Operation(summary = "댓글 작성")
    @PostMapping("new")
    public RestResult<?> createReply(@RequestBody ReplyCreateReqDto replyCreateReqDto) {
        Long replyId = replyService.createReply(MemberContextHolder.getMemberId(), replyCreateReqDto);
        return RestResult.success(new IdResDto(replyId));
    }

    @Operation(summary = "댓글 또는 대댓글 수정")
    @PutMapping("modify")
    public RestResult<?> modifyReply(@RequestBody ReplyModifyReqDto replyModifyReqDto) {
        ReplyModiResDto replyModi = replyService.modify(replyModifyReqDto);
        return RestResult.success(replyModi);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("delete")
    public RestResult<?> deleteReply(@RequestParam Long replyId) {
        Long deletedId = replyService.delete(replyId);
        return RestResult.success(new IdResDto(deletedId));
    }

    @Operation(summary = "대댓글 작성")
    @PostMapping("toReply/new")
    public RestResult<?> createReplyToReply(@RequestBody ReplyToReplyCreateReqDto replyCreateDto) {
        Long replyToReplyId = replyService.createReplyToReply(MemberContextHolder.getMemberId(), replyCreateDto);
        return RestResult.success(new IdResDto(replyToReplyId));
    }

    @Operation(summary = "대댓글 삭제")
    @DeleteMapping("toReply/delete/")
    public RestResult<?> deleteReReply(@RequestParam Long replyId) {
        Long deletedId = replyService.deleteReplyToReply(replyId);
        return RestResult.success(new IdResDto(deletedId));
    }

    @Operation(summary = "회원이 작성한 댓글 목록")
    @GetMapping("wrote/list")
    public RestResult<?> replyListByMember(@RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ReplyByMemberResDto> replies = replyService.repliesByMember(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(replies);
    }

}
