package salaba.domain.rentalHome.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import salaba.domain.rentalHome.dto.response.RentalHomeResDto;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalHomeRepositoryCustom {
    RentalHomeDetailResDto findDetailById(Long rentalHomeId);
    RentalHomeDetailResDto findDetailByIdAndHost(Long rentalHomeId, Long hostId);

    Page<RentalHome> findByHost(Long hostId, Pageable pageable);

    Optional<RentalHome> findWithReservations(Long rentalHomeId);

    Page<RentalHome> findRentalHomesOrderByReview(String regionName, String themeName, Long minPrice, Long maxPrice, Pageable pageable);

    Page<RentalHomeResDto> findRentalHomeDtosOrderByReview(String regionName, String themeName, Long minPrice, Long maxPrice, Pageable pageable);

    Page<RentalHome> findRentalHomesOrderBySalesCount(String regionName, String themeName, Long minPrice, Long maxPrice, Pageable pageable);
}
