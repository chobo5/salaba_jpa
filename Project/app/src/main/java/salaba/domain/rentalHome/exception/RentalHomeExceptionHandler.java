package salaba.domain.rentalHome.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import salaba.util.RestResult;

@RestControllerAdvice
@Slf4j
public class RentalHomeExceptionHandler {

    @ExceptionHandler(CannotChangeStatusException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 또는 적절한 상태 코드 설정
    @ResponseBody
    public RestResult<?> handleCannotChangeStatusException(CannotChangeStatusException e) {
        return RestResult.error(e.getMessage());
    }
}
