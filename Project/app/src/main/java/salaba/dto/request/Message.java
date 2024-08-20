package salaba.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class Message {
    private int statusCode;
    private String message;
}
