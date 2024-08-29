package salaba.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.RentalHomeModiReqDto;
import salaba.service.RentalHomeService;
import salaba.service.ReservationService;
import salaba.util.RestResult;
@Api(tags = "호스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/host/")
public class HostController {

    private final RentalHomeService rentalHomeService;

    private final ReservationService reservationService;

    @GetMapping("rentalHome/list/{hostId}")
    public RestResult<?> getRentalHomeList(@PathVariable Long hostId) {
        return RestResult.success(rentalHomeService.getByHost(hostId));
    }

    @GetMapping("rentalHome/detail/{rentalHomeId}")
    public RestResult<?> getRentalHomeDetail(@PathVariable Long rentalHomeId) {
        return RestResult.success(rentalHomeService.get(rentalHomeId));
    }

    @PutMapping("rentalHome/modify")
    public RestResult<?> modifyRentalHome(@RequestBody RentalHomeModiReqDto rentalHomeModiReqDto) {
        return RestResult.success(rentalHomeService.modifyRentalHome(rentalHomeModiReqDto));
    }

    @DeleteMapping("rentalHome/delete/{rentalHomeId}")
    public RestResult<?> deleteRentalHome(@PathVariable Long rentalHomeId) {
        return RestResult.success(rentalHomeService.deleteRentalHome(rentalHomeId));
    }

    @GetMapping("rentalHome/reservation/list/{rentalHomeId}")
    public RestResult<?> reservationList(@PathVariable Long rentalHomeId, Pageable pageable) {
        return RestResult.success(reservationService.getWithGuest(rentalHomeId, pageable));
    }


}
