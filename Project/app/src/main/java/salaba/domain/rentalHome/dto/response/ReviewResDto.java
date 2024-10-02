package salaba.domain.rentalHome.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import salaba.domain.reservation.entity.Review;

import java.time.LocalDateTime;

@Data
public class ReviewResDto {
    private Long reviewId;
    private int score;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public ReviewResDto(Review review) {
        reviewId = review.getId();
        score = review.getScore();
        content = review.getContent();
        createdDate = review.getCreatedDate();
    }
}
