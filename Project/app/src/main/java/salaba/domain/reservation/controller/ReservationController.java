package salaba.domain.reservation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import salaba.domain.reservation.dto.ReservedDateDto;
import salaba.domain.reservation.dto.request.DiscountReqDto;
import salaba.domain.reservation.dto.request.PaymentReqDto;
import salaba.domain.reservation.dto.request.ReservationReqDto;
import salaba.domain.reservation.dto.response.ReservationCompleteResDto;
import salaba.domain.reservation.dto.response.ReservationResForGuestDto;
import salaba.domain.reservation.dto.response.ReservationResForHostDto;
import salaba.domain.reservation.service.ReservationService;
import salaba.domain.common.dto.IdResDto;
import salaba.interceptor.MemberContextHolder;
import salaba.util.RestResult;

import java.util.List;

@Tag(name = "숙소예약 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @Operation(summary = "숙소 예약하기")
    @PostMapping("new")
    public RestResult<?> makeReservation(@RequestBody ReservationReqDto reservationReqDto) {
        Long reservationId = reservationService.makeReservation(MemberContextHolder.getMemberId(), reservationReqDto);
        return RestResult.success(new IdResDto(reservationId));
    }

    @Operation(summary = "결제 완료")
    @PostMapping("complete")
    public RestResult<?> recordPayment(@RequestBody PaymentReqDto reqDto) {
        ReservationCompleteResDto resDto = reservationService.completeReservation(MemberContextHolder.getMemberId(), reqDto);
        return RestResult.success(resDto);
    }

    @Operation(summary = "숙소 이미 예약되어있는 날짜 목록")
    @GetMapping("/api/v1/reservedDate/{rentalHomeId}/{status}")
    public RestResult<?> getReservedDates(@RequestParam Long rentalHomeId) {
        List<ReservedDateDto> reservedDates = reservationService.getReservedDate(rentalHomeId);
        return RestResult.success(reservedDates);
    }

    @Operation(summary = "회원의 예약 목록")
    @GetMapping("reservation/list")
    public RestResult<?> reservationList(@RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ReservationResForGuestDto> reservations = reservationService.getWithRentalHomeForGuest(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(reservations);
    }

    @Operation(summary = "호스트의 모든 숙소 예약 목록")
    @GetMapping("host/reservation/list")
    public RestResult<?> reservationListForHost(@RequestParam(defaultValue = "0") int pageNumber,
                                                @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ReservationResForHostDto> reservations = reservationService.getWithRentalHomeForHost(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(reservations);
    }

    @Operation(summary = "예약 취소")
    @DeleteMapping("reservation/cancel")
    public RestResult<?> cancelReservation(@RequestParam Long reservationId) {
        reservationService.cancelReservation(reservationId, MemberContextHolder.getMemberId());
        return RestResult.success();
    }
}
