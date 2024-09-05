package salaba.domain.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardResDto {
    private Long boardId;

    private String title;

    private String content;

    private String writerNickname;

    private int viewCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    private Long likeCount;

    private Long replyCount;

    @QueryProjection
    public BoardResDto(Long boardId, String title, String content, String writerNickname, int viewCount, LocalDateTime createdDate, Long likeCount, Long replyCount) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writerNickname = writerNickname;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
    }
}
