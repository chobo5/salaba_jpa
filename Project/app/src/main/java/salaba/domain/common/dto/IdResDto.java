package salaba.domain.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdResDto {
    Long id;

    public IdResDto(Long id) {
        this.id = id;
    }
}