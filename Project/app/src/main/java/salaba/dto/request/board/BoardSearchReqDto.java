package salaba.dto.request.board;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "게시물 검색 DTO(title or writer중 1개만 전달")
public class BoardSearchReqDto {
    @Schema(description = "게시글 제목")
    String title;
    @Schema(description = "게시글 작성자 닉네임")
    String writer;
}
