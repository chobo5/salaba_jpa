package salaba.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.RentalHomeCreateReqDto;
import salaba.dto.request.RentalHomeModiReqDto;
import salaba.dto.response.IdResDto;
import salaba.service.HostService;
import salaba.service.RentalHomeService;
import salaba.service.ReservationService;
import salaba.util.MemberContextHolder;
import salaba.util.RestResult;
@Tag(name = "호스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/host/")
public class HostController {

    private final HostService hostService;

    private final ReservationService reservationService;

    private final RentalHomeService rentalHomeService;

    @Operation(summary = "숙소 등록")
    @PostMapping("new")
    public RestResult<?> createRentalHome(@RequestBody RentalHomeCreateReqDto rentalHomeCreateReqDto) {
        return RestResult.success(new IdResDto(hostService.createRentalHome(MemberContextHolder.getMemberId(), rentalHomeCreateReqDto)));
    }

    @Operation(summary = "호스트 소유 숙소 목록")
    @GetMapping("rentalHome/list/{hostId}")
    public RestResult<?> getRentalHomeList(@PathVariable Long hostId) {
        return RestResult.success(hostService.getByHost(hostId));
    }

    @Operation(summary = "호스트 소유 숙소 상세")
    @GetMapping("rentalHome/detail/{rentalHomeId}")
    public RestResult<?> getRentalHomeDetail(@PathVariable Long rentalHomeId) {
        return RestResult.success(rentalHomeService.get(rentalHomeId));
    }

    @Operation(summary = "호스트 소유 숙소 수정")
    @PutMapping("rentalHome/modify")
    public RestResult<?> modifyRentalHome(@RequestBody RentalHomeModiReqDto rentalHomeModiReqDto) {
        return RestResult.success(hostService.modifyRentalHome(rentalHomeModiReqDto));
    }

    @Operation(summary = "호스트 소유 숙소 폐쇄(삭제)")
    @DeleteMapping("rentalHome/delete/{rentalHomeId}")
    public RestResult<?> deleteRentalHome(@PathVariable Long rentalHomeId) {
        return RestResult.success(hostService.deleteRentalHome(MemberContextHolder.getMemberId(), rentalHomeId));
    }

    @Operation(summary = "호스트 소유 숙소 예약 목록")
    @GetMapping("rentalHome/reservation/list/{rentalHomeId}")
    public RestResult<?> reservationList(@PathVariable Long rentalHomeId, Pageable pageable) {
        return RestResult.success(reservationService.getWithGuest(rentalHomeId, pageable));
    }


}
