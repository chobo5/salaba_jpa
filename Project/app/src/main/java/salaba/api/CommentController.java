package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salaba.dto.board.CommentCreateDto;
import salaba.response.IdResponse;
import salaba.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment/")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("new")
    public ResponseEntity<?> createComment(@RequestBody CommentCreateDto commentCreateDto) {
        return ResponseEntity.ok(new IdResponse(commentService.createComment(commentCreateDto)));
    }
}
