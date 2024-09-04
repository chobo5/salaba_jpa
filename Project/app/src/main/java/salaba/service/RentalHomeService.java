package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import salaba.dto.response.ReviewResDto;
import salaba.entity.rental.*;
import salaba.repository.jpa.rentalHome.RentalHomeRepository;
import salaba.repository.jpa.rentalHome.ReviewRepository;
import salaba.dto.response.RentalHomeDetailResDto;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RentalHomeService {
    private final RentalHomeRepository rentalHomeRepository;
    private final ReviewRepository reviewRepository;

    public RentalHomeDetailResDto get(Long rentalHomeId) {
        return rentalHomeRepository.findDetailById(rentalHomeId);
    }

    public Page<ReviewResDto> getRentalHomeReviews(Long rentalHomeId, Pageable pageable) {
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId).orElseThrow(NoSuchElementException::new);
        Page<Review> reviews = reviewRepository.findByRentalHome(rentalHome, pageable);
        return reviews.map(ReviewResDto::new);
    }

    public Double getRentalHomeReviewAvg(Long rentalHomeId) {
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId).orElseThrow(NoSuchElementException::new);
        return reviewRepository.getReviewAvg(rentalHome);
    }
}
