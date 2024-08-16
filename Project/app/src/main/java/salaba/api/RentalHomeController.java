package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salaba.dto.RentalHomeCreateDto;
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
}
