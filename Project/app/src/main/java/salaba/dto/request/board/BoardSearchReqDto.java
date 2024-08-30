package salaba.dto.request.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("게시물 검색 DTO(title or writer중 1개만 전달")
public class BoardSearchReqDto {
    @ApiModelProperty("게시글 제목")
    String title;
    @ApiModelProperty("게시글 작성자 닉네임")
    String writer;
}
