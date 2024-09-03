package salaba.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.board.ReplyCreateReqDto;
import salaba.dto.request.board.ReplyModifyReqDto;
import salaba.dto.request.board.ReplyToReplyCreateReqDto;
import salaba.dto.response.IdResDto;
import salaba.service.ReplyService;
import salaba.util.MemberContextHolder;
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
        return RestResult.success(new IdResDto(replyService.createReply(MemberContextHolder.getMemberId(), replyCreateReqDto)));
    }

    @Operation(summary = "댓글 또는 대댓글 수정")
    @PutMapping("modify")
    public RestResult<?> modifyReply(@RequestBody ReplyModifyReqDto replyModifyReqDto) {
        return RestResult.success(replyService.modify(replyModifyReqDto));
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("delete/{id}")
    public RestResult<?> deleteReply(@PathVariable Long id) {
        return RestResult.success(new IdResDto(replyService.delete(id)));
    }

    @Operation(summary = "대댓글 작성")
    @PostMapping("toReply/new")
    public RestResult<?> createReplyToReply(@RequestBody ReplyToReplyCreateReqDto replyCreateDto) {
        return RestResult.success(new IdResDto(replyService.createReplyToReply(MemberContextHolder.getMemberId(), replyCreateDto)));
    }

    @Operation(summary = "대댓글 삭제")
    @DeleteMapping("toReply/delete/{id}")
    public RestResult<?> deleteReReply(@PathVariable Long id) {
        return RestResult.success(new IdResDto(replyService.deleteReplyToReply(id)));
    }

}
