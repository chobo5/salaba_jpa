package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import salaba.dto.request.RentalHomeCreateReqDto;
import salaba.dto.request.RentalHomeModiReqDto;
import salaba.dto.request.ReviewReqDto;
import salaba.dto.response.RentalHomeResDto;
import salaba.dto.response.ReviewResDto;
import salaba.entity.Address;
import salaba.entity.Region;
import salaba.entity.member.Member;
import salaba.entity.rental.*;
import salaba.exception.NoAuthorityException;
import salaba.repository.*;
import salaba.repository.rentalHome.*;
import salaba.dto.response.RentalHomeDetailResDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalHomeService {
    private final RentalHomeRepository rentalHomeRepository;
    private final ReviewRepository reviewRepository;

    public RentalHomeDetailResDto get(Long rentalHomeId) {
        return rentalHomeRepository.get(rentalHomeId);
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
