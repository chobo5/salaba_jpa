package salaba.domain.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import salaba.util.RestResult;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 또는 적절한 상태 코드 설정
    @ResponseBody
    public RestResult<?> handleNoSuchElementException(NoSuchElementException e) {
        return RestResult.error("Cannot Find Entity");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 또는 적절한 상태 코드 설정
    @ResponseBody
    public RestResult<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return RestResult.error(e.getMessage());
    }


}
