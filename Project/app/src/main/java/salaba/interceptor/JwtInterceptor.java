package salaba.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import salaba.security.jwt.util.JwtTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtTokenizer jwtTokenizer;

    @Override
    //Controller 동작 이전 작업
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            Long memberId = jwtTokenizer.getMemberIdFromToken(token);
            MemberContextHolder.setMemberId(memberId);
        }
        return true; //true 반환시 다음 인터셉터 또는 컨트롤러 실행
    }

    @Override
    //DispatcherSerlvet의 화면 처리(뷰)가 완료된 상태에서 처리
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //Object handler - 현재 실행하려 메소드 자체를 의미
        MemberContextHolder.clear();
    }
}
