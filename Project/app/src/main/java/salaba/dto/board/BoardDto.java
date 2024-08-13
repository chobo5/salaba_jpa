package salaba.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardDto {
    private Long boardId;

    private String title;

    private String content;

    private String writerNickname;

    private int viewCount;

    private LocalDateTime createdDate;

    private Long likeCount;

    private Long commentCount;

    @QueryProjection
    public BoardDto(Long boardId, String title, String content, String writerNickname, int viewCount, LocalDateTime createdDate, Long likeCount, Long commentCount) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writerNickname = writerNickname;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}
