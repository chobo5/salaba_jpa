package salaba.domain.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.domain.board.constants.BoardScope;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class BoardDetailResDto {

    private Long boardId;

    private BoardScope scope;

    private String title;

    private String content;

    private Long writerId;

    private String writerNickname;

    private Integer viewCount;

    private Long likeCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    private List<ReplyResDto> replyList;

    public BoardDetailResDto(Long boardId, BoardScope scope, String title, String content, Long writerId, String writerNickname, Integer viewCount, Long likeCount, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.scope = scope;
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.createdDate = createdDate;
    }
}
