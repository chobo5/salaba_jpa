package salaba.domain.member.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import salaba.domain.auth.exception.CannotFindMemberException;
import salaba.domain.auth.exception.NoAuthorityException;
import salaba.util.RestResult;

import javax.validation.ValidationException;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class MemberExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 또는 적절한 상태 코드 설정
    @ResponseBody
    public RestResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = Optional.ofNullable(e.getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse("Validation error");
        return RestResult.error(message);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 또는 적절한 상태 코드 설정
    @ResponseBody
    public RestResult<?> handleAlreadyExistsException(AlreadyExistsException e) {
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(CannotFindMemberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 또는 적절한 상태 코드 설정
    @ResponseBody
    public RestResult<?> handleCannotFindMemberException(CannotFindMemberException e) {
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(NoAuthorityException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 또는 적절한 상태 코드 설정
    @ResponseBody
    public RestResult<?> handleNoAuthorityException(NoAuthorityException e) {
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 또는 적절한 상태 코드 설정
    @ResponseBody
    public RestResult<?> handleValidationException(ValidationException e) {
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(PointCannotBeZeroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 또는 적절한 상태 코드 설정
    @ResponseBody
    public RestResult<?> handleCannotBeZeroException(PointCannotBeZeroException e) {
        return RestResult.error(e.getMessage());
    }


}
