package salaba.domain.reservation.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.reservation.entity.Review;

import java.util.Optional;

@Repository
public interface ReviewRepositoryCustom {
    Page<Review> findByMember(Member member, Pageable pageable);

    Page<Review> findByRentalHome(RentalHome rentalHome, Pageable pageable);

    Double getReviewAvg(RentalHome rentalHome);

    Optional<Review> findByIdWithReservationAndMemberAndRentalHome(Long reviewId);
}
