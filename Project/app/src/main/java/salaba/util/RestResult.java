package salaba.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RestResult<T> {
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    private String status;
    private T data;
    private String error;

    @Builder.Default //빌더로 인스턴스 생성시 초기화할 값 지정
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime timestamp = LocalDateTime.now();


    public static <T> RestResult<T> success(T data) {
        //제네릭 메서드에서 타입 매개변수를 사용할 때는 반환 타입 앞에 <T>와 같이 타입 매개변수를 선언해주어야 한다.
        return RestResult.<T>builder() //현재 메서드가 사용할 타입 매개변수를 선언
                .status(SUCCESS)
                .data(data)
                .build();
    }

    public static RestResult<Void> success() {
            return RestResult.<Void>builder()
                    .status(SUCCESS)
                    .build();
    }

    public static RestResult<?> error(String errorMessage){
        return RestResult.builder()
                .status(FAILURE)
                .error(errorMessage)
                .build();
    }


}
