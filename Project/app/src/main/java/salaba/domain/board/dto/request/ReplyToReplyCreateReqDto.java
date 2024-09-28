package salaba.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "대댓글 생성 요청 DTO")
public class ReplyToReplyCreateReqDto {
    @Schema(description = "대상 댓글 id")
    @NotNull
    private Long replyId;

    @Schema(description = "대댓글 내용")
    @NotEmpty
    private String content;
}
