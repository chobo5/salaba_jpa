package salaba.domain.rentalHome.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.global.constants.ProcessStatus;
import salaba.domain.global.entity.Address;
import salaba.domain.global.entity.Region;
import salaba.domain.global.exception.ErrorMessage;
import salaba.domain.global.repository.RegionRepository;
import salaba.domain.member.entity.Member;
import salaba.domain.member.repository.MemberRepository;
import salaba.domain.rentalHome.dto.request.RentalHomeCreateReqDto;
import salaba.domain.rentalHome.dto.request.RentalHomeModiReqDto;
import salaba.domain.rentalHome.dto.response.RentalHomeDetailResDto;
import salaba.domain.rentalHome.dto.response.RentalHomeResDto;
import salaba.domain.rentalHome.entity.*;
import salaba.domain.rentalHome.exception.CannotChangeStatusException;
import salaba.domain.rentalHome.repository.*;
import salaba.domain.auth.exception.NoAuthorityException;
import salaba.domain.rentalHome.repository.query.RentalHomeQueryRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import salaba.domain.reservation.entity.Reservation;
import salaba.domain.reservation.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RentalHomeService {
    private final MemberRepository memberRepository;
    private final RentalHomeRepository rentalHomeRepository;
    private final RentalHomeQueryRepository rentalHomeQueryRepository;
    private final RegionRepository regionRepository;
    private final RentalHomeThemeService rentalHomeThemeService;
    private final RentalHomeFacilityService rentalHomeFacilityService;
    private final ReservationRepository reservationRepository;

    public RentalHomeDetailResDto view(Long rentalHomeId) {
        return rentalHomeQueryRepository.findDetailById(rentalHomeId);
    }

    public Long create(Long memberId, RentalHomeCreateReqDto reqDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));

        Region region = regionRepository.findById(reqDto.getRegionId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Region.class, reqDto.getRegionId())));

        // 숙소 생성 후 저장
        RentalHome rentalHome = RentalHome.create(member, region, reqDto.getName(),
                reqDto.getExplanation(), new Address(reqDto.getStreet(), reqDto.getZipcode()), reqDto.getPrice(),
                reqDto.getCapacity(), reqDto.getLat(), reqDto.getLon(), reqDto.getRule(), reqDto.getCleanFee());

        rentalHomeRepository.save(rentalHome);

        // 숙소_테마 저장
        rentalHomeThemeService.saveAll(rentalHome, reqDto.getThemes());

        // 숙소_시설 저장
        rentalHomeFacilityService.saveAll(rentalHome, reqDto.getFacilities());

        return rentalHome.getId();
    }

    public RentalHomeDetailResDto modify(Long memberId, RentalHomeModiReqDto reqDto) {
        //숙소의 호스트를 찾는다.
        Member host = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));
        //숙소의 id와 호스트를 통해 숙소를 찾는다.
        RentalHome rentalHome = rentalHomeRepository.findByIdAndHost(reqDto.getRentalHomeId(), host)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(RentalHome.class, reqDto.getRentalHomeId())));

        Region region = reqDto.getRegionId() != null ?
                regionRepository.findById(reqDto.getRegionId())
                        .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Region.class, reqDto.getRegionId())))
                : null;

        Address address = reqDto.getStreet() != null && reqDto.getZipcode() != null ?
                new Address(reqDto.getStreet(), reqDto.getZipcode()) : null;

        rentalHome.modify(region, reqDto.getName(), reqDto.getExplanation(), address, reqDto.getPrice(),
                reqDto.getCapacity(), reqDto.getLat(), reqDto.getLon(), reqDto.getRule(), reqDto.getCleanFee());

        // 숙소_테마 저장
        if (reqDto.getThemes() != null && !reqDto.getThemes().isEmpty()) {
            rentalHomeThemeService.deleteAll(rentalHome);
            List<RentalHomeTheme> rentalHomeThemes = rentalHomeThemeService.saveAll(rentalHome, reqDto.getThemes());
        }

        // 숙소_시설 저장
        if (reqDto.getFacilities() != null && !reqDto.getFacilities().isEmpty()) {
            rentalHomeFacilityService.deleteAll(rentalHome);
            List<RentalHomeFacility> rentalHomeFacilities = rentalHomeFacilityService.saveAll(rentalHome, reqDto.getFacilities());
        }

        return new RentalHomeDetailResDto(rentalHome);
    }

    public Long delete(Long memberId, Long rentalHomeId) {
        Member host = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(Member.class, memberId)));
        RentalHome rentalHome = rentalHomeRepository.findById(rentalHomeId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.entityNotFound(RentalHome.class, rentalHomeId)));
        List<Reservation> reservations = reservationRepository.findByRentalHomeAndStatus(rentalHome,
                ProcessStatus.COMPLETE);

        if (!rentalHome.getHost().equals(host)) {
            throw new NoAuthorityException("숙소 삭제 권한이 없습니다.");
        }

        reservations.forEach(reservation -> {
            if (reservation.getEndDate().isAfter(LocalDateTime.now())) {
                throw new CannotChangeStatusException("이용중이거나 예약된 게스트가 있어 삭제가 불가능합니다.");
            }
        });

        rentalHome.closeRentalHome();
        return rentalHome.getId();
    }

    public Page<RentalHomeResDto> getRentalHomesOwnedByHost(Long hostId, Pageable pageable) {
        Page<RentalHome> rentalHomes = rentalHomeQueryRepository.findByHost(hostId, pageable);

        return rentalHomes.map(RentalHomeResDto::new);
    }

    public RentalHomeDetailResDto getRentalHomeOwnedByHost(Long memberId, Long rentalHomeId) {
        return rentalHomeQueryRepository.findDetailByIdAndHost(rentalHomeId, memberId);
    }

    public Page<RentalHomeResDto> searchRentalHomesOrderByReview(String regionName, String themeName, Long minPrice,
                                                                 Long maxPrice, Pageable pageable) {
        return rentalHomeQueryRepository.findRentalHomeDtosOrderByReview(regionName, themeName, minPrice, maxPrice, pageable);
    }

    public Page<RentalHomeResDto> searchRentalHomesOrderBySalesCount(String regionName, String themeName, Long minPrice,
                                                                     Long maxPrice, Pageable pageable) {
        Page<RentalHome> rentalHomes = rentalHomeQueryRepository
                .findRentalHomesOrderBySalesCount(regionName, themeName, minPrice, maxPrice, pageable);

        return rentalHomes.map(RentalHomeResDto::new);
    }
}
