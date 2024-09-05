package salaba.domain.reply.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ReplyToReplyResDto {
    private Long id;

    private Long parentId;

    private Long writerId;

    private String writerNickname;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public ReplyToReplyResDto(Long id, Long parentId, Long writerId, String writerNickname, String content, LocalDateTime createdDate) {
        this.id = id;
        this.parentId = parentId;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.content = content;
        this.createdDate = createdDate;
    }
}
