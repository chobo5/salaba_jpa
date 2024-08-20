package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.board.ReplyCreateDto;
import salaba.dto.request.board.ReplyModifyDto;
import salaba.dto.request.board.ReplyToReplyCreateDto;
import salaba.dto.response.IdResDto;
import salaba.service.ReplyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reply/")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("new")
    public ResponseEntity<?> createReply(@RequestBody ReplyCreateDto replyCreateDto) {
        return ResponseEntity.ok(new IdResDto(replyService.createReply(replyCreateDto)));
    }

    @PutMapping("modify")
    public ResponseEntity<?> modifyReply(@RequestBody ReplyModifyDto replyModifyDto) {
        return ResponseEntity.ok(replyService.modify(replyModifyDto));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteReply(@PathVariable Long id) {
        return ResponseEntity.ok(new IdResDto(replyService.delete(id)));
    }

    @PostMapping("toReply/new")
    public ResponseEntity<?> createReplyToReply(@RequestBody ReplyToReplyCreateDto replyCreateDto) {
        return ResponseEntity.ok(new IdResDto(replyService.createReplyToReply(replyCreateDto)));
    }

    @DeleteMapping("toReply/delete/{id}")
    public ResponseEntity<?> deleteReReply(@PathVariable Long id) {
        return ResponseEntity.ok(new IdResDto(replyService.deleteReplyToReply(id)));
    }

}
