package salaba.repository.rentalHome;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import salaba.entity.member.BookMark;
import salaba.entity.member.Member;
import salaba.entity.rental.RentalHome;

import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    BookMark findByMemberAndRentalHome (Member member, RentalHome rentalHome);
    Long deleteByMemberAndRentalHome (Member member, RentalHome rentalHome);

}
