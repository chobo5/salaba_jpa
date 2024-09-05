package salaba.domain.rentalHome.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.entity.Review;

public interface ReviewRepositoryCustom {
    Page<Review> findByMember(Member member, Pageable pageable);

    Page<Review> findByRentalHome(RentalHome rentalHome, Pageable pageable);

    Double getReviewAvg(RentalHome rentalHome);
}
