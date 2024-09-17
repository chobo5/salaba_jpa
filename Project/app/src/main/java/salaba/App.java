/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package salaba;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import salaba.domain.common.service.InitService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
@EnableCaching
@EnableAspectJAutoProxy
@PropertySource({
        "file:${user.home}/config/jdbc.properties",
        "file:${user.home}/config/jwt.properties"
})
public class App {
//    private final InitService initService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

//    @PostConstruct
//    public void init() {
//        initService.init();
//    }

//    @Bean
//    public AuditorAware<String> auditorProvider() {
//        try {
//            return () -> Optional.of(MemberContextHolder.getMemberId().toString());
//        } catch (CannotFindMemberException e) {
//            return () -> Optional.of("new");
//        }
//
//    }


}
