package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salaba.dto.RentalHomeCreateDto;
import salaba.dto.RentalHomeModifyDto;
import salaba.response.IdResponse;
import salaba.service.RentalHomeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentalHome")
public class RentalHomeController {
    private final RentalHomeService rentalHomeService;
    @PostMapping("new")
    public ResponseEntity<?> createRentalHome(@RequestBody RentalHomeCreateDto rentalHomeCreateDto) {
        return ResponseEntity.ok(new IdResponse(rentalHomeService.createRentalHome(rentalHomeCreateDto)));
    }

    @PutMapping("modify")
    public ResponseEntity<?> modifyRentalHome(@RequestBody RentalHomeModifyDto rentalHomeModifyDto) {
        return ResponseEntity.ok(rentalHomeService.modifyRentalHome(rentalHomeModifyDto));
    }
}
