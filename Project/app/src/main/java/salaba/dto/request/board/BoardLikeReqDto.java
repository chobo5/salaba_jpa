package salaba.dto.request.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("게시물 좋아요 요청 DTO")
public class BoardLikeReqDto {
    @ApiModelProperty("대상 게시물 id")
    @NotNull
    private Long boardId;

    @ApiModelProperty("회원 id")
    @NotNull
    private Long memberId;
}
