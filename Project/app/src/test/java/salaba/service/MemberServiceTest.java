package salaba.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import salaba.domain.member.dto.request.MemberJoinReqDto;
import salaba.domain.member.service.AuthService;

import javax.validation.ValidationException;
import java.time.LocalDate;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    AuthService authService;

    @Test
    public void joinSuccessTest() {
        authService.join(
                new MemberJoinReqDto("흥판봉", "원준연", "wnsdus1008@gmail.com",
                        "Tt12241509!@", LocalDate.of(1996, 10, 8)));

    }

    @Test
    public void joinFailTest() {
        MemberJoinReqDto member = new MemberJoinReqDto("흥판봉", "원준연", "wnsdus1008@gmail.com",
                "1234", LocalDate.of(1996, 10, 8));

        Assertions.assertThrows(ValidationException.class, () -> authService.join(member));


    }


}