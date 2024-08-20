package salaba.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.board.BoardCategory;
import salaba.entity.board.BoardScope;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardCreateDto {
    private Long memberId;
    private String title;
    private String content;
    private BoardCategory category;
    private BoardScope scope;
}
