package salaba.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import salaba.dto.MemberJoinDto;
import salaba.exception.PasswordValidationException;

import java.time.LocalDate;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void joinSuccessTest() {
        memberService.join(
                new MemberJoinDto("흥판봉", "원준연", "wnsdus1008@gmail.com",
                        "Tt12241509!@", LocalDate.of(1996, 10, 8)));

    }

    @Test
    public void joinFailTest() {
        MemberJoinDto member = new MemberJoinDto("흥판봉", "원준연", "wnsdus1008@gmail.com",
                "1234", LocalDate.of(1996, 10, 8));

        Assertions.assertThrows(PasswordValidationException.class, () -> memberService.join(member));


    }


}