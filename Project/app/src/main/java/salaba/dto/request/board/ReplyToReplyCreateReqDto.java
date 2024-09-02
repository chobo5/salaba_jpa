package salaba.dto.request.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("대댓글 생성 요청 DTO")
public class ReplyToReplyCreateReqDto {
    @ApiModelProperty("대상 댓글 id")
    @NotNull
    private Long replyId;

    @ApiModelProperty("작성자(회원) id")
    @NotNull
    private Long memberId;

    @ApiModelProperty("대댓글 내용")
    @NotEmpty
    private String content;
}
