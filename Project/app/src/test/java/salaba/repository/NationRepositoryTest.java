package salaba.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import salaba.entity.Nation;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class NationRepositoryTest {

    @Autowired
    private NationRepository nationRepository;
    @Test
    public void insertNation() {
        List<Nation> nations = new ArrayList<>();
        nations.add(Nation.builder().nationNo(1).nationName("USA").build());
        nations.add(Nation.builder().nationNo(7).nationName("RUSSIA").build());
        nations.add(Nation.builder().nationNo(33).nationName("FRANCE").build());
        nations.add(Nation.builder().nationNo(34).nationName("SPAIN").build());
        nations.add(Nation.builder().nationNo(44).nationName("UK").build());
        nations.add(Nation.builder().nationNo(49).nationName("GERMANY").build());
        nations.add(Nation.builder().nationNo(82).nationName("KOREA").build());
        nations.add(Nation.builder().nationNo(81).nationName("JAPAN").build());
        nations.add(Nation.builder().nationNo(86).nationName("CHINA").build());
        nations.add(Nation.builder().nationNo(61).nationName("AUSTRALIA").build());
        nationRepository.saveAll(nations);
    }
}
