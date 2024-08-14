package salaba.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateResponse {
    Long id;

    public CreateResponse (Long id) {
        this.id = id;
    }
}
