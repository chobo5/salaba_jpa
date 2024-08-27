package salaba.dto.request;

import lombok.Data;
import lombok.Getter;
import salaba.entity.ProcessStatus;
import salaba.entity.rental.PayMethod;
import salaba.entity.rental.Reservation;
import salaba.exception.CannotChangeStatusException;

import javax.persistence.*;
import java.util.List;

@Data
public class PaymentReqDto {

    private Long reservationId;

    private String paymentId;

    private int amount;

    private ProcessStatus status;

    private PayMethod method;

    private List<DiscountReqDto> discounts;


}
