package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.ReservationReqDto;
import salaba.dto.response.IdResDto;
import salaba.entity.ProcessStatus;
import salaba.service.ReservationService;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/api/v1/reservation")
    public ResponseEntity<?> makeRentalHomeReservation(@RequestBody ReservationReqDto reservationReqDto) {
        return ResponseEntity.ok(new IdResDto(reservationService.makeReservation(reservationReqDto)));
    }

    @GetMapping("/api/v1/reservedDate/{rentalHomeId}/{status}")
    public ResponseEntity<?> getReservedDate(@PathVariable Long rentalHomeId, @PathVariable ProcessStatus status) {
        return ResponseEntity.ok(reservationService.getReservedDate(rentalHomeId, status));
    }
}
