package salaba.dto;

import lombok.*;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinDto{

    private String nickname;

    private String name;

    private String email;

    private String password;

    private LocalDate birthday;

}
