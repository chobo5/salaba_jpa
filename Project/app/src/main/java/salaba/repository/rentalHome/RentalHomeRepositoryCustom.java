package salaba.repository.rentalHome;

import salaba.dto.response.RentalHomeDetailResDto;
import salaba.entity.member.Member;
import salaba.entity.rental.RentalHome;

import java.util.List;
import java.util.Optional;


public interface RentalHomeRepositoryCustom {
    RentalHomeDetailResDto get(Long rentalHomeId);

    List<RentalHome> findByHost(Long memberId);

    Optional<RentalHome> findWithReservations(Long rentalHomeId);
}
