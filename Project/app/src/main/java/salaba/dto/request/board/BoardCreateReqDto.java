package salaba.dto.request.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.board.BoardScope;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("게시물 생성 요청 DTO")
public class BoardCreateReqDto {
    @ApiModelProperty("작성자(회원) id")
    private Long memberId;
    
    @ApiModelProperty("게시물 제목")
    private String title;

    @ApiModelProperty("게시물 내용")
    private String content;

    @ApiModelProperty("게시물 공개 범위")
    private BoardScope scope;
}
