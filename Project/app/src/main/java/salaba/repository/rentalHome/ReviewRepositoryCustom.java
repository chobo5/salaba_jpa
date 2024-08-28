package salaba.repository.rentalHome;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import salaba.entity.member.Member;
import salaba.entity.rental.RentalHome;
import salaba.entity.rental.Review;

public interface ReviewRepositoryCustom {
    Page<Review> findByMember(Member member, Pageable pageable);

    Page<Review> findByRentalHome(RentalHome rentalHome, Pageable pageable);

    Double getReviewAvg(RentalHome rentalHome);
}
