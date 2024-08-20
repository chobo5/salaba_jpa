package salaba.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberModiReqDto {

    private Long id;
    private String name;
    private Integer nationId;
    private Gender gender;
    private String street;
    private Integer zipcode;

}
