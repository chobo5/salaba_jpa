package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salaba.dto.request.RentalHomeCreateReqDto;
import salaba.dto.request.RentalHomeModiReqDto;
import salaba.entity.rental.RentalHome;
import salaba.dto.response.IdResDto;
import salaba.service.RentalHomeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentalHome/")
public class RentalHomeController {
    private final RentalHomeService rentalHomeService;
    @PostMapping("new")
    public ResponseEntity<?> createRentalHome(@RequestBody RentalHomeCreateReqDto rentalHomeCreateReqDto) {
        return ResponseEntity.ok(new IdResDto(rentalHomeService.createRentalHome(rentalHomeCreateReqDto)));
    }

    @PutMapping("modify")
    public ResponseEntity<?> modifyRentalHome(@RequestBody RentalHomeModiReqDto rentalHomeModiReqDto) {
        return ResponseEntity.ok(rentalHomeService.modifyRentalHome(rentalHomeModiReqDto));
    }

    @GetMapping("{rentalHomeId}")
    public ResponseEntity<?> getRentalHome(@PathVariable Long rentalHomeId) {
        return ResponseEntity.ok(rentalHomeService.get(rentalHomeId));

    }
}
