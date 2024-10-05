package salaba.domain.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.domain.board.constants.BoardScope;
import salaba.domain.board.entity.Board;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardModiResDto {
    private Long boardId;
    private String title;
    private String content;
    private BoardScope boardScope;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedDate;

    public BoardModiResDto(Board board) {
        boardId = board.getId();
        title = board.getTitle();
        content = board.getContent();
        boardScope = board.getBoardScope();
        createdDate = board.getCreatedDate();
        updatedDate = board.getUpdatedDate();
    }
}
