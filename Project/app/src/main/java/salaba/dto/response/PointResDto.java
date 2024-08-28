package salaba.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import salaba.entity.member.Point;

import java.time.LocalDateTime;

@Data
public class PointResDto {
    private Long pointId;

    private int amount;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public PointResDto(Point point) {
        pointId = point.getId();
        amount = point.getAmount();
        content = point.getContent();
        createdDate = point.getCreatedDate();
    }


}
