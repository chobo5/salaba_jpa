package salaba.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReplyDto {
    private Long replyNo;
    private String content;
    private String writerNickName;
    private LocalDateTime createdDate;
}
