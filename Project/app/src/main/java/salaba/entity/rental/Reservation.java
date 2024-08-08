package salaba.entity.rental;

import lombok.Getter;
import salaba.entity.ProcessStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private ProcessStatus status;





}
