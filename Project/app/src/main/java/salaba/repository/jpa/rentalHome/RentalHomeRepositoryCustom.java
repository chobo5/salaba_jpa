package salaba.repository.jpa.rentalHome;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import salaba.dto.response.RentalHomeDetailResDto;
import salaba.entity.rental.RentalHome;

import java.util.Optional;


public interface RentalHomeRepositoryCustom {
    RentalHomeDetailResDto findDetailById(Long rentalHomeId);
    RentalHomeDetailResDto findDetailByIdAndHost(Long rentalHomeId, Long hostId);

    Page<RentalHome> findByHost(Long hostId, Pageable pageable);

    Optional<RentalHome> findWithReservations(Long rentalHomeId);
}
