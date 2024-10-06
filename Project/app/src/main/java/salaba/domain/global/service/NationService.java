package salaba.domain.global.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.global.dto.NationDto;
import salaba.domain.global.entity.Nation;
import salaba.domain.global.repository.NationRepository;
import salaba.domain.member.exception.AlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NationService {
    private final NationRepository nationRepository;

    public List<NationDto> getList() {
        List<Nation> nationList = nationRepository.findAll();

        return nationList.stream()
                .map(NationDto::new)
                .collect(Collectors.toList());
    }

    public NationDto add(NationDto nationDto) {
        Optional<Nation> existingNationById = nationRepository.findById(nationDto.getId());
        if (existingNationById.isPresent()) {
            throw new AlreadyExistsException("이미 해당 id에 존재하는 국가가 있습니다.");
        }
        Optional<Nation> existingNationByName = nationRepository.findByNameContaining(nationDto.getName());
        if (existingNationByName.isPresent()) {
            throw new AlreadyExistsException("이미 존재하는 국가입니다.");
        }
        Nation nation = new Nation(nationDto.getId(), nationDto.getName());
        nationRepository.save(nation);
        return nationDto;
    }
}
