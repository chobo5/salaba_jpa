package salaba.domain.rentalHome.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.rentalHome.entity.RentalHome;
import salaba.domain.rentalHome.entity.RentalHomeTheme;
import salaba.domain.rentalHome.entity.Theme;
import salaba.domain.rentalHome.repository.RentalHomeThemeRepository;
import salaba.domain.rentalHome.repository.ThemeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RentalHomeThemeService {
    private final ThemeRepository themeRepository;
    private final RentalHomeThemeRepository rentalHomeThemeRepository;

    public List<RentalHomeTheme> saveAll(RentalHome rentalHome, List<Long> themeIds) {
        List<Theme> themes = themeRepository.findAllById(themeIds);
        List<RentalHomeTheme> rentalHomeThemes = themes.stream()
                .map(theme -> RentalHomeTheme.create(rentalHome, theme))
                .collect(Collectors.toList());
        return rentalHomeThemeRepository.saveAll(rentalHomeThemes);
    }

    public void deleteAll(RentalHome rentalHome) {
        rentalHomeThemeRepository.deleteByRentalHome(rentalHome);
    }
}
