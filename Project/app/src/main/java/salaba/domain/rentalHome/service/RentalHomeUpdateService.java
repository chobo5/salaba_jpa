package salaba.domain.rentalHome.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import salaba.domain.rentalHome.repository.RentalHomeRepository;

@Service
@RequiredArgsConstructor
public class RentalHomeUpdateService {
    private final RentalHomeRepository rentalHomeRepository;

    @Scheduled(cron = "0 0 4 * * ?") // 매일 4시 정각에 실행
    public void updateReviewStatistics() {
        rentalHomeRepository.updateReviewStatistics();
    }


}
