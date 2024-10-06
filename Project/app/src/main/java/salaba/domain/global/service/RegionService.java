package salaba.domain.global.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.global.dto.RegionCreateDto;
import salaba.domain.global.dto.RegionDto;
import salaba.domain.global.entity.Nation;
import salaba.domain.global.entity.Region;
import salaba.domain.global.repository.NationRepository;
import salaba.domain.global.repository.RegionRepository;
import salaba.domain.member.exception.AlreadyExistsException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RegionService {
    private final RegionRepository regionRepository;
    private final NationRepository nationRepository;

    public List<RegionDto> getList(Integer nationId) {
        Nation nation = nationRepository.findById(nationId).orElseThrow(EntityNotFoundException::new);
        List<Region> regions = regionRepository.findByNation(nation);
        return  regions.stream()
                .map(RegionDto::new)
                .collect(Collectors.toList());
    }

    public RegionDto add(RegionCreateDto reqDto) {
        Nation nation = nationRepository.findById(reqDto.getNationId()).orElseThrow(EntityNotFoundException::new);

        Optional<Nation> existingNationByName = nationRepository.findByNameContaining(reqDto.getName());
        if (existingNationByName.isPresent()) {
            throw new AlreadyExistsException("이미 존재하는 지역입니다.");
        }
        Region region = new Region(reqDto.getName(), nation);
        regionRepository.save(region);
        return new RegionDto(region);
    }
}
