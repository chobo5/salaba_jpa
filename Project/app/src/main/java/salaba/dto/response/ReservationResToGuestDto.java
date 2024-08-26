package salaba.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import salaba.entity.ProcessStatus;
import salaba.entity.rental.Reservation;

import java.time.LocalDateTime;

@Data
public class ReservationResToGuestDto {
    private Long reservationId;

    private String rentalHomeName;

    private String hostName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDate;

    private ProcessStatus status;


    public ReservationResToGuestDto(Reservation reservation) {
        reservationId = reservation.getId();
        rentalHomeName = reservation.getRentalHome().getName();
        hostName = reservation.getRentalHome().getHost().getName();
        startDate = reservation.getStartDate();
        endDate = reservation.getEndDate();
        status = reservation.getStatus();
    }
}
