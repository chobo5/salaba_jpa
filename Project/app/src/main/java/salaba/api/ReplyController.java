package salaba.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.board.ReplyCreateReqDto;
import salaba.dto.request.board.ReplyModifyReqDto;
import salaba.dto.request.board.ReplyToReplyCreateReqDto;
import salaba.dto.response.IdResDto;
import salaba.service.ReplyService;
import salaba.util.RestResult;

@Api(tags = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reply/")
public class ReplyController {
    private final ReplyService replyService;

    @ApiOperation("댓글 작성")
    @PostMapping("new")
    public RestResult<?> createReply(@RequestBody ReplyCreateReqDto replyCreateReqDto) {
        return RestResult.success(new IdResDto(replyService.createReply(replyCreateReqDto)));
    }

    @ApiOperation("댓글 또는 대댓글 수정")
    @PutMapping("modify")
    public RestResult<?> modifyReply(@RequestBody ReplyModifyReqDto replyModifyReqDto) {
        return RestResult.success(replyService.modify(replyModifyReqDto));
    }

    @ApiOperation("댓글 삭제")
    @DeleteMapping("delete/{id}")
    public RestResult<?> deleteReply(@PathVariable Long id) {
        return RestResult.success(new IdResDto(replyService.delete(id)));
    }

    @ApiOperation("대댓글 작성")
    @PostMapping("toReply/new")
    public RestResult<?> createReplyToReply(@RequestBody ReplyToReplyCreateReqDto replyCreateDto) {
        return RestResult.success(new IdResDto(replyService.createReplyToReply(replyCreateDto)));
    }

    @ApiOperation("대댓글 삭제")
    @DeleteMapping("toReply/delete/{id}")
    public RestResult<?> deleteReReply(@PathVariable Long id) {
        return RestResult.success(new IdResDto(replyService.deleteReplyToReply(id)));
    }

}
