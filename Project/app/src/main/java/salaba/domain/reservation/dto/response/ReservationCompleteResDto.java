package salaba.domain.reservation.dto.response;

import lombok.Data;
import salaba.domain.reservation.entity.Reservation;
import java.time.LocalDateTime;

@Data
public class ReservationCompleteResDto {
    private String guestName;
    private String rentalHomeName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int finalPrice;

    public ReservationCompleteResDto(Reservation reservation) {
        guestName = reservation.getMember().getName();
        rentalHomeName = reservation.getRentalHome().getName();
        startDate = reservation.getStartDate();
        endDate = reservation.getEndDate();
        finalPrice = reservation.getFinalPrice();
    }
}
