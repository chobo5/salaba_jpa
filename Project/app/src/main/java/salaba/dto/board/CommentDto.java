package salaba.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentDto {
    private Long commentNo;
    private String content;
    private String writerNickName;
    private LocalDateTime createdDate;
    private List<ReplyDto> replyDtoList;
}
