package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salaba.dto.board.ReplyCreateDto;
import salaba.dto.board.ReplyToReplyCreateDto;
import salaba.response.IdResponse;
import salaba.service.ReplyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reply/")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("new")
    public ResponseEntity<?> createReply(@RequestBody ReplyCreateDto replyCreateDto) {
        return ResponseEntity.ok(new IdResponse(replyService.createReply(replyCreateDto)));
    }

    @PostMapping("toReply/new")
    public ResponseEntity<?> createReplyToReply(@RequestBody ReplyToReplyCreateDto replyCreateDto) {
        return ResponseEntity.ok(new IdResponse(replyService.createReplyToReply(replyCreateDto)));
    }

}
