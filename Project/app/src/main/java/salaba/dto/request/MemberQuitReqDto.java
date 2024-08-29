package salaba.dto.request;

import lombok.Data;

@Data
public class MemberQuitReqDto {
    private String email;
    private String password;
}
