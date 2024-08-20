package salaba.dto.request.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyToReplyCreateDto {
    private Long replyId;
    private Long memberId;
    private String content;
}
