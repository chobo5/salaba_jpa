package salaba.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import salaba.entity.ProcessStatus;
import salaba.entity.rental.Reservation;

import java.time.LocalDateTime;

@Data
public class ReservationToHostResDto {
    private Long reservationId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDate;

    private ProcessStatus status;

    private String guestName;

    public ReservationToHostResDto(Reservation reservation) {
        reservationId = reservation.getId();
        startDate = reservation.getStartDate();
        endDate = reservation.getEndDate();
        status = reservation.getStatus();
        guestName = reservation.getMember().getName();
    }

}
