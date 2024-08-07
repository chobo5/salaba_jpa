package org.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.admin.domain.Rental;
import org.admin.service.RentalService;
import org.admin.util.RentalType;
import org.admin.util.RestResult;
import org.admin.util.SearchType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rental")
@Slf4j
public class RentalManageController {
    private final RentalService rentalService;

    @GetMapping("/list/{rentalType}")
    public RestResult<?> rentalList(@PathVariable int rentalType) {

            if (rentalType == RentalType.REGISTERED.getValue()) {
                return RestResult.success(rentalService.getAll());
            }

            if (rentalType == RentalType.WAITING_REG.getValue()) {
                return RestResult.success(rentalService.getAppliedRentals());
            }

            return RestResult.error("유효하지 않은 rentalType 입니다.");
    }

    @GetMapping("/view/{menu}/{rentalNo}")
    public RestResult<?> rentalView(@PathVariable int rentalNo) {
        return RestResult.success(rentalService.getBy(rentalNo));
    }

    @PutMapping("/update")
    public RestResult<?> rentalUpdate(@RequestBody Rental rental) {
        rentalService.updateState(rental.getRentalNo(), rental.getState());
        return RestResult.success();
    }

    @GetMapping("/search/{keyword}/{filter}")
    public RestResult<?> searchRental(@PathVariable String keyword,
                                      @PathVariable String filter) {
        if (filter.equals(SearchType.NAME.getValue())) {
            // 숙소명으로 검색
            return RestResult.success(rentalService.getAllByName(keyword));
        } else {
            // 호스트명로 검색
            return RestResult.success(rentalService.getAllByHostName(keyword));
        }
    }
}
