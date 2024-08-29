package salaba.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.ReservationReqDto;
import salaba.dto.response.IdResDto;
import salaba.entity.ProcessStatus;
import salaba.service.ReservationService;
import salaba.util.RestResult;

@Api(tags = "숙소예약 API")
@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/api/v1/reservation")
    public RestResult<?> makeRentalHomeReservation(@RequestBody ReservationReqDto reservationReqDto) {
        return RestResult.success(new IdResDto(reservationService.makeReservation(reservationReqDto)));
    }

    @GetMapping("/api/v1/reservedDate/{rentalHomeId}/{status}")
    public RestResult<?> getReservedDate(@PathVariable Long rentalHomeId, @PathVariable ProcessStatus status) {
        return RestResult.success(reservationService.getReservedDate(rentalHomeId, status));
    }
}
