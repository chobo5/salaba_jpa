package salaba.dto.request.board;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "댓글 생성 요청 DTO")
public class ReplyCreateReqDto {
    @Schema(description = "대상 게시물 id")
    @NotNull
    private Long boardId;

    @Schema(description = "작성자(회원) id")
    @NotNull
    private Long memberId;

    @Schema(description = "댓글 내용")
    @NotEmpty
    private String content;
}
