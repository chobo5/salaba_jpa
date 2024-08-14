package salaba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import salaba.entity.Address;
import salaba.entity.Gender;
import salaba.entity.Nation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberModifyDto {

    private Long id;
    private String name;
    private Integer nationId;
    private Gender gender;
    private Address address;

}
