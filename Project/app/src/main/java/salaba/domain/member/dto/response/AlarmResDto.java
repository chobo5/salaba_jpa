package salaba.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import salaba.domain.member.entity.Alarm;

import java.time.LocalDateTime;

@Data
public class AlarmResDto {
    private Long alarmId;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public AlarmResDto(Alarm alarm) {
        alarmId = alarm.getId();
        content = alarm.getContent();
        createdDate = alarm.getCreatedDate();
    }
}
