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
public class BoardDetailDto {

    private Long boardNo;

    private int headNo;

    private String headName;

    private int scopeNo;

    private int categoryNo;

    private String categoryName;

    private String title;

    private String content;

    private Long writerNo;

    private String writerNickname;

    private int viewCount;

    private int likeCount;

    private LocalDateTime createdDate;

    private List<CommentDto> commentDtoList;

}
