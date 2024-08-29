package salaba.dto.request;

import lombok.Data;

@Data
public class ChangePasswordReqDto {
    private Long id;
    private String password;
}
