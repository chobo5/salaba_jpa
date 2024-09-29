package salaba.warmup;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class WarmUpRequest {
    @AssertTrue
    private boolean validTrue;

    @AssertFalse
    private boolean validFalse;

    @NotBlank
    @Pattern(regexp = "warm up")
    private String validString;

    @Min(10)
    @Max(20)
    private int validNumber;

    @NotNull
    private BigDecimal validBigDecimal;
}
