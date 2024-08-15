package salaba.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentCreateDto {
    private Long boardId;
    private Long memberId;
    private String content;
}
