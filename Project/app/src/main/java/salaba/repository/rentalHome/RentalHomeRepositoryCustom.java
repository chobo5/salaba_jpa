package salaba.repository.rentalHome;

import salaba.dto.response.RentalHomeDetailResDto;
import salaba.entity.member.Member;
import salaba.entity.rental.RentalHome;

import java.util.List;


public interface RentalHomeRepositoryCustom {
    RentalHomeDetailResDto get(Long rentalHomeId);

    List<RentalHome> findByHost(Long memberId);
}
