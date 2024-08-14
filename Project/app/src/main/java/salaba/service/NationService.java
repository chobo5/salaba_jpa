package salaba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.NationDto;
import salaba.entity.Nation;
import salaba.repository.NationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NationService {
    private final NationRepository nationRepository;

    public List<NationDto> list() {
        List<Nation> nationList = nationRepository.findAll();

        return nationList.stream()
                .map(nation -> new NationDto(nation.getId(), nation.getName()))
                .collect(Collectors.toList());
    }
}
