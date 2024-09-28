package salaba.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "댓글 수정 요청 DTO")
public class ReplyModifyReqDto {
    @Schema(description = "대상 댓글 id")
    @NotNull
    private Long replyId;

    @Schema(description = "댓글 내용")
    @NotEmpty
    private String content;
}
