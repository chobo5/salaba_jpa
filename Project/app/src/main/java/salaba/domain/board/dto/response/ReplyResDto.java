package salaba.domain.board.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class ReplyResDto {
    private Long id;
    private Long boardId;

    private Long writerId;

    private String writerNickname;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    private List<ReplyToReplyResDto> replyToReplyList;

    @QueryProjection
    public ReplyResDto(Long id, Long boardId, Long writerId, String writerNickname, String content, LocalDateTime createdDate) {
        this.id = id;
        this.boardId = boardId;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.content = content;
        this.createdDate = createdDate;
    }
}
