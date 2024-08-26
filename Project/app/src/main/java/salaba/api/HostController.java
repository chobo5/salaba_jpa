package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.RentalHomeModiReqDto;
import salaba.service.RentalHomeService;
import salaba.service.ReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/host/")
public class HostController {

    private final RentalHomeService rentalHomeService;

    private final ReservationService reservationService;

    @GetMapping("rentalHome/list/{hostId}")
    public ResponseEntity<?> getRentalHomeList(@PathVariable Long hostId) {
        return ResponseEntity.ok(rentalHomeService.getByHost(hostId));
    }

    @GetMapping("rentalHome/detail/{rentalHomeId}")
    public ResponseEntity<?> getRentalHomeDetail(@PathVariable Long rentalHomeId) {
        return ResponseEntity.ok(rentalHomeService.get(rentalHomeId));
    }

    @PutMapping("rentalHome/modify")
    public ResponseEntity<?> modifyRentalHome(@RequestBody RentalHomeModiReqDto rentalHomeModiReqDto) {
        return ResponseEntity.ok(rentalHomeService.modifyRentalHome(rentalHomeModiReqDto));
    }

    @DeleteMapping("rentalHome/delete/{rentalHomeId}")
    public ResponseEntity<?> deleteRentalHome(@PathVariable Long rentalHomeId) {
        return ResponseEntity.ok(rentalHomeService.deleteRentalHome(rentalHomeId));
    }

    @GetMapping("rentalHome/reservation/list/{rentalHomeId}")
    public ResponseEntity<?> reservationList(@PathVariable Long rentalHomeId, Pageable pageable) {
        return ResponseEntity.ok(reservationService.getWithGuest(rentalHomeId, pageable));
    }


}
