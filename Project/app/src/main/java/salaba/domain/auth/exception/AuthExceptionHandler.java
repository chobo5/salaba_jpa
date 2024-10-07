package salaba.domain.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import salaba.domain.member.exception.AlreadyExistsException;
import salaba.domain.member.exception.PointCannotBeZeroException;
import salaba.util.RestResult;

import javax.validation.ValidationException;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {


    @ExceptionHandler(BlockedAccountException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public RestResult<?> handleBlockedAccountException(BlockedAccountException e) {
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(ResignedAccountException.class)
    @ResponseStatus(HttpStatus.GONE)
    @ResponseBody
    public RestResult<?> handleResignedAccountException(ResignedAccountException e) {
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(SleepingAccountException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    @ResponseBody
    public RestResult<?> handleSleepingAccountException(SleepingAccountException e) {
        return RestResult.error(e.getMessage());
    }


}
