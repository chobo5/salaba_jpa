package salaba.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import salaba.entity.member.Role;

import java.util.List;

@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findAllRoles() {
        List<Role> roles = roleRepository.findAll();
        roles.forEach(role -> System.out.println(role));
    }
}
