package salaba.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.board.ReplyCreateDto;
import salaba.dto.request.board.ReplyModifyDto;
import salaba.dto.request.board.ReplyToReplyCreateDto;
import salaba.dto.response.IdResDto;
import salaba.service.ReplyService;
import salaba.util.RestResult;

@Api(tags = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reply/")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("new")
    public RestResult<?> createReply(@RequestBody ReplyCreateDto replyCreateDto) {
        return RestResult.success(new IdResDto(replyService.createReply(replyCreateDto)));
    }

    @PutMapping("modify")
    public RestResult<?> modifyReply(@RequestBody ReplyModifyDto replyModifyDto) {
        return RestResult.success(replyService.modify(replyModifyDto));
    }

    @DeleteMapping("delete/{id}")
    public RestResult<?> deleteReply(@PathVariable Long id) {
        return RestResult.success(new IdResDto(replyService.delete(id)));
    }

    @PostMapping("toReply/new")
    public RestResult<?> createReplyToReply(@RequestBody ReplyToReplyCreateDto replyCreateDto) {
        return RestResult.success(new IdResDto(replyService.createReplyToReply(replyCreateDto)));
    }

    @DeleteMapping("toReply/delete/{id}")
    public RestResult<?> deleteReReply(@PathVariable Long id) {
        return RestResult.success(new IdResDto(replyService.deleteReplyToReply(id)));
    }

}
