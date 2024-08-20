package salaba.dto.request.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.board.BoardCategory;
import salaba.entity.board.BoardScope;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardModifyDto {
    private Long boardId;
    private String title;
    private String content;
    private BoardScope boardScope;
}
