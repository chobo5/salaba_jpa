package salaba.dto.request;

import lombok.Data;

@Data
public class ReviewReqDto {
    private Long reservationId;

    private String content;

    private int score;
}
