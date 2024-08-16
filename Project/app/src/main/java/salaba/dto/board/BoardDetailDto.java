package salaba.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.board.Board;
import salaba.entity.board.BoardCategory;
import salaba.entity.board.BoardScope;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class BoardDetailDto {

    private Long boardId;

    private BoardScope scope;

    private BoardCategory category;

    private String title;

    private String content;

    private Long writerId;

    private String writerNickname;

    private Integer viewCount;

    private Long likeCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    private List<ReplyDto> replyList;

    public BoardDetailDto(Long boardId, BoardScope scope, BoardCategory category, String title, String content, Long writerId, String writerNickname, Integer viewCount, Long likeCount, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.scope = scope;
        this.category = category;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.createdDate = createdDate;
    }
}
