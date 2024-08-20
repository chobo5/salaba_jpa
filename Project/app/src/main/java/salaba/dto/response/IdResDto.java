package salaba.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdResponse {
    Long id;

    public IdResponse(Long id) {
        this.id = id;
    }
}
