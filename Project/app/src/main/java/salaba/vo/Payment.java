package salaba.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private static final long serialVersionUID = 100L;
    private long reservationNo;
    private String paymentNo;
    private Date paymentDate;
    private long amount;
    private char state;
    private String payMethod;
}
