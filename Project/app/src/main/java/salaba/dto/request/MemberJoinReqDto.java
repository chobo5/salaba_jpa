package salaba.dto.request;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinReqDto {

    private String nickname;

    private String name;

    private String email;

    private String password;

    private LocalDate birthday;

}
