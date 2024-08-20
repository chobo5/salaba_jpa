package salaba.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class ReplyToReplyDto {
    private Long id;

    private Long parentId;

    private Long writerId;

    private String writerNickname;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public ReplyToReplyDto(Long id, Long parentId, Long writerId, String writerNickname, String content, LocalDateTime createdDate) {
        this.id = id;
        this.parentId = parentId;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.content = content;
        this.createdDate = createdDate;
    }
}
