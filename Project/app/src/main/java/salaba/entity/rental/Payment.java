package salaba.entity.rental;

import lombok.Getter;
import salaba.entity.ProcessStatus;

import javax.persistence.*;

@Entity
@Getter
public class Payment {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    private int amount;

    @Enumerated(EnumType.STRING)
    private ProcessStatus status;


}
