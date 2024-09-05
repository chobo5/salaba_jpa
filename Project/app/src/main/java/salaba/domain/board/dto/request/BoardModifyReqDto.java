package salaba.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.domain.board.constants.BoardScope;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시물 수정 요청 DTO")
public class BoardModifyReqDto {
    @Schema(description = "수정할 게시물 id")
    @NotNull
    private Long boardId;

    @Schema(description = "게시물 제목")
    @NotEmpty
    private String title;

    @Schema(description = "게시물 내용")
    @NotEmpty
    private String content;

    @Schema(description = "게시물 공개 범위")
    @NotNull
    private BoardScope boardScope;
}
