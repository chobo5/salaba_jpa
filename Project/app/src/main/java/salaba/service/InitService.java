package salaba.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import salaba.entity.Nation;
import salaba.entity.member.Role;
import salaba.entity.member.RoleName;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class InitService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void init() {
        Nation korea = new Nation(82, "kr");
        Nation japan = new Nation(81, "jp");
        Nation usa = new Nation(1, "us");
        Nation china = new Nation(86, "ch");
        Nation germany = new Nation(49, "ger");
        Nation uk = new Nation(44, "uk");
        Nation russia = new Nation(7, "rus");
        em.persist(korea);
        em.persist(japan);
        em.persist(usa);
        em.persist(china);
        em.persist(germany);
        em.persist(uk);
        em.persist(russia);

        Role member = new Role(RoleName.MEMBER.getId(), RoleName.MEMBER);
        Role manager = new Role(RoleName.MANAGER.getId(), RoleName.MANAGER);
        Role admin = new Role(RoleName.ADMIN.getId(), RoleName.ADMIN);
        em.persist(member);
        em.persist(manager);
        em.persist(admin);
    }

}
