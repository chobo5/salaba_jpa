package salaba.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import salaba.service.NationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nation")
public class NationController {

    private final NationService nationService;

    @GetMapping("list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(nationService.list());
    }
}
