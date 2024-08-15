package salaba.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    private Long writerNo;

    private String writerNickname;

    private int viewCount;

    private int likeCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    private List<ReplyDto> replyList;

}
