package salaba.domain.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import salaba.global.constants.ProcessStatus;
import salaba.domain.reservation.entity.Reservation;

import java.time.LocalDateTime;

@Data
public class ReservationResForHostDto {
    private Long reservationId;

    private String rentalHomeName;

    private String guestName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDate;

    private ProcessStatus status;


    public ReservationResForHostDto(Reservation reservation) {
        reservationId = reservation.getId();
        rentalHomeName = reservation.getRentalHome().getName();
        guestName = reservation.getMember().getName();
        startDate = reservation.getStartDate();
        endDate = reservation.getEndDate();
        status = reservation.getStatus();
    }
}
