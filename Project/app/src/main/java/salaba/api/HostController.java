package salaba.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.RentalHomeCreateReqDto;
import salaba.dto.request.RentalHomeModiReqDto;
import salaba.dto.response.IdResDto;
import salaba.dto.response.RentalHomeDetailResDto;
import salaba.dto.response.RentalHomeResDto;
import salaba.service.HostService;
import salaba.service.RentalHomeService;
import salaba.service.ReservationService;
import salaba.util.MemberContextHolder;
import salaba.util.RestResult;

import java.util.List;

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
    @GetMapping("rentalHome/list")
    public RestResult<?> getRentalHomeList(@RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<RentalHomeResDto> rentalHomes = hostService.getRentalHomesByHost(MemberContextHolder.getMemberId(), pageable);
        return RestResult.success(rentalHomes);
    }

    @Operation(summary = "호스트 소유 숙소 상세")
    @GetMapping("rentalHome/detail/{rentalHomeId}")
    public RestResult<?> getRentalHomeDetail(@PathVariable Long rentalHomeId) {
        RentalHomeDetailResDto rentalHomeDetail = hostService.getRentalHomeByHost(MemberContextHolder.getMemberId(), rentalHomeId);
        return RestResult.success(rentalHomeDetail);
    }

    @Operation(summary = "호스트 소유 숙소 수정")
    @PutMapping("rentalHome/modify")
    public RestResult<?> modifyRentalHome(@RequestBody RentalHomeModiReqDto rentalHomeModiReqDto) {
        return RestResult.success(hostService.modifyRentalHome(MemberContextHolder.getMemberId(), rentalHomeModiReqDto));
    }

    @Operation(summary = "호스트 소유 숙소 폐쇄(삭제)")
    @DeleteMapping("rentalHome/delete/")
    public RestResult<?> deleteRentalHome(@RequestParam Long rentalHomeId) {
        return RestResult.success(hostService.deleteRentalHome(MemberContextHolder.getMemberId(), rentalHomeId));
    }

//    @Operation(summary = "호스트 소유 숙소 예약 목록")
//    @GetMapping("rentalHome/reservation/list/")
//    public RestResult<?> reservationList(@RequestParam Long rentalHomeId,
//                                         @RequestParam(defaultValue = "0") int pageNumber,
//                                         @RequestParam(defaultValue = "10") int pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        hostService.getRentalHomesByHost(MemberContextHolder.getMemberId(), rentalHomeId, pageable);
//        return RestResult.success();
//    }


}
