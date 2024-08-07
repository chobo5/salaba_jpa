package salaba.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private Long boardNo;

    private String headName;

    private int categoryNo;

    private String title;

    private String content;

    private String writerNickname;

    private int viewCount;

    private Long likeCount;

    private Long commentCount;

    private LocalDateTime createdDate;

}
