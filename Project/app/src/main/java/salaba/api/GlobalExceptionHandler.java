package salaba.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import salaba.exception.AlreadyExistsException;
import salaba.exception.CannotChangeStatusException;
import salaba.exception.ValidationException;
import salaba.util.RestResult;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public RestResult<?> handleNoSuchElementException(NoSuchElementException e) {
        return RestResult.error("Cannot Find Entity");
    }

    @ExceptionHandler(ValidationException.class)
    public RestResult<?> handleValidationException(ValidationException e) {
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public RestResult<?> handleAlreadyExistsException(AlreadyExistsException e) {
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(CannotChangeStatusException.class)
    public RestResult<?> handleCannotChangeStatusException(CannotChangeStatusException e) {
        return RestResult.error(e.getMessage());
    }


}
