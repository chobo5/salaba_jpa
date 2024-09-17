package salaba.domain.rentalHome.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.entity.Review;

@Repository
public interface ReviewRepositoryCustom {
    Page<Review> findByMember(Member member, Pageable pageable);

    Page<Review> findByRentalHome(RentalHome rentalHome, Pageable pageable);

    Double getReviewAvg(RentalHome rentalHome);
}
