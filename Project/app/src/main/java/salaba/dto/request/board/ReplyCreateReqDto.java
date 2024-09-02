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
@ApiModel("댓글 생성 요청 DTO")
public class ReplyCreateReqDto {
    @ApiModelProperty("대상 게시물 id")
    @NotNull
    private Long boardId;

    @ApiModelProperty("작성자(회원) id")
    @NotNull
    private Long memberId;

    @ApiModelProperty("댓글 내용")
    @NotEmpty
    private String content;
}
