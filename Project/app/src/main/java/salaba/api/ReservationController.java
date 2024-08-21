package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import salaba.dto.request.ReservationReqDto;
import salaba.service.ReservationService;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/api/v1/reservation")
    public ResponseEntity<?> makeRentalHomeReservation(@RequestBody ReservationReqDto reservationReqDto) {
        return ResponseEntity.ok(reservationService.makeReservation(reservationReqDto));
    }
}
