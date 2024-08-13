package salaba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MemberJoinDto {

    private String nickname;

    private String name;

    private String email;

    private String password;

    private LocalDate birthday;
}
