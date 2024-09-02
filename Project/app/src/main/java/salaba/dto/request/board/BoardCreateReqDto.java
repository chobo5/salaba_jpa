package salaba.dto.request.board;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.board.BoardScope;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시물 생성 요청 DTO")
public class BoardCreateReqDto {
    @Schema(description = "작성자(회원) id")
    @NotNull
    private Long memberId;
    
    @Schema(description = "게시물 제목")
    @NotEmpty
    private String title;

    @Schema(description = "게시물 내용")
    @NotEmpty
    private String content;

    @Schema(description = "게시물 공개 범위")
    @NotNull
    private BoardScope scope;
}
