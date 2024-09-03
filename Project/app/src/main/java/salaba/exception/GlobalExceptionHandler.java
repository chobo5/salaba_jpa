package salaba.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import salaba.exception.*;
import salaba.util.RestResult;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public RestResult<?> handleNoSuchElementException(NoSuchElementException e) {
        return RestResult.error("Cannot Find Entity");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = Optional.ofNullable(e.getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse("Validation error");
        return RestResult.error(message);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public RestResult<?> handleAlreadyExistsException(AlreadyExistsException e) {
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(CannotChangeStatusException.class)
    public RestResult<?> handleCannotChangeStatusException(CannotChangeStatusException e) {
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(CannotFindMemberException.class)
    public RestResult<?> handleCannotFindMemberException(CannotFindMemberException e) {
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(PasswordNotCorrectException.class)
    public RestResult<?> handlePasswordNotCorrectException(PasswordNotCorrectException e) {
        return RestResult.error(e.getMessage());
    }

    @ExceptionHandler(NoAuthorityException.class)
    public RestResult<?> handleNoAuthorityException(NoAuthorityException e) {
        return RestResult.error(e.getMessage());
    }


}
