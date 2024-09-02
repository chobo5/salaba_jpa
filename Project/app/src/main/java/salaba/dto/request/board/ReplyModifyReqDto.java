package salaba.dto.request.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("댓글 수정 요청 DTO")
public class ReplyModifyReqDto {
    @ApiModelProperty("대상 댓글 id")
    @NotNull
    private Long replyId;

    @ApiModelProperty("댓글 내용")
    @NotEmpty
    private String content;
}
