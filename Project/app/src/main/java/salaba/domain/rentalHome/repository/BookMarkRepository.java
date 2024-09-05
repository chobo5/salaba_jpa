package salaba.domain.rentalHome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.domain.member.entity.Member;
import salaba.domain.rentalHome.entity.BookMark;
import salaba.domain.rentalHome.entity.RentalHome;

import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    Optional<BookMark> findByMemberAndRentalHome (Member member, RentalHome rentalHome);

}
