package salaba.domain.board.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시물 좋아요 요청 DTO")
public class BoardLikeReqDto {
    @Schema(description = "대상 게시물 id")
    @NotNull
    private Long boardId;

}
