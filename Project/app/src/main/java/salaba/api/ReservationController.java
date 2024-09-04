package salaba.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import salaba.dto.ReservedDateDto;
import salaba.dto.request.ReservationReqDto;
import salaba.dto.response.IdResDto;
import salaba.util.MemberContextHolder;
import salaba.util.ProcessStatus;
import salaba.service.ReservationService;
import salaba.util.RestResult;

import java.util.List;

@Tag(name = "숙소예약 API")
@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @Operation(summary = "숙소 예약하기")
    @PostMapping("/api/v1/reservation")
    public RestResult<?> makeRentalHomeReservation(@RequestBody ReservationReqDto reservationReqDto) {
        Long reservationId = reservationService.makeReservation(MemberContextHolder.getMemberId(), reservationReqDto);
        return RestResult.success(new IdResDto(reservationId));
    }

    @Operation(summary = "숙소 이미 예약되어있는 날짜 목록")
    @GetMapping("/api/v1/reservedDate/{rentalHomeId}/{status}")
    public RestResult<?> getReservedDate(@RequestParam Long rentalHomeId) {
        List<ReservedDateDto> reservedDates = reservationService.getReservedDate(rentalHomeId);
        return RestResult.success(reservedDates);
    }
}
