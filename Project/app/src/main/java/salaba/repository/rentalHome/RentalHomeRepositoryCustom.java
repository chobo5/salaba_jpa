package salaba.repository.rentalHome;

import org.springframework.stereotype.Repository;
import salaba.dto.response.RentalHomeResDto;
import salaba.entity.rental.RentalHome;

import java.util.Optional;


public interface RentalHomeRepositoryCustom {
    RentalHomeResDto get(Long rentalHomeId);
}
