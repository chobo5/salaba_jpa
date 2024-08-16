package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dto.RentalHomeCreateDto;
import salaba.entity.Region;
import salaba.entity.member.Member;
import salaba.entity.rental.RentalHome;
import salaba.repository.MemberRepository;
import salaba.repository.RegionRepsitory;
import salaba.repository.RentalHomeRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RentalHomeService {
    private final RentalHomeRepository rentalHomeRepository;
    private final MemberRepository memberRepository;
    private final RegionRepsitory regionRepsitory;

    public Long createRentalHome(RentalHomeCreateDto dto) {
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(NoSuchElementException::new);
        Region region = regionRepsitory.findById(dto.getRegionId()).orElseThrow(NoSuchElementException::new);
        RentalHome rentalHome = RentalHome.createRentalHome(member, region, dto.getName(),
                dto.getExplanation(), dto.getAddress(), dto.getPrice(),
                dto.getCapacity(), dto.getLat(), dto.getLon(),
                dto.getHostingStartDate(), dto.getHostingEndDate(), dto.getRule(), dto.getCleanFee());
        rentalHomeRepository.save(rentalHome);
        return rentalHome.getId();
    }
}
