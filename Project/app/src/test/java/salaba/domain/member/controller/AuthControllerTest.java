package salaba.domain.member.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import salaba.domain.member.service.AuthService;
import salaba.exception.AlreadyExistsException;
import salaba.exception.GlobalExceptionHandler;
import salaba.security.jwt.util.JwtTokenizer;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(GlobalExceptionHandler.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtTokenizer jwtTokenizer;


    @Test
    @WithMockUser
    public void 닉네임사용가능여부조회_성공() throws Exception {
        // given
        String nickname = "testNickname";

        // when - 서비스의 동작을 목처리
        doNothing().when(authService).isExistingNickname(nickname);

        // then - 요청을 보내고 결과를 검증
        mockMvc.perform(get("/api/v1/auth/validateNickname")
                        .param("nickname", nickname))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"success\"}"));

        // 서비스 메서드가 호출되었는지 검증
        verify(authService, times(1)).isExistingNickname(nickname);

    }

//    @Test
//    @WithMockUser
//    public void 닉네임사용가능여부조회_실패() throws Exception {
//        // given
//        String nickname = "testNickname";
//
//        // when - 서비스의 동작을 목처리
//        doThrow(new AlreadyExistsException()).when(authService).isExistingNickname(nickname);
//
//        // then - 요청을 보내고 결과를 검증
//        mockMvc.perform(get("/api/v1/auth/validateNickname")
//                        .param("nickname", "invalid_nickname"))
//                .andExpect(status().isConflict())
//                .andExpect(jsonPath("status").value("failure"));
//
//        // 서비스 메서드가 호출되었는지 검증
//        verify(authService, times(1)).isExistingNickname(nickname);
//
//    }

    @Test
    @WithMockUser
    public void 이메일사용가능여부조회() throws Exception {
        // given
        String email = "test@test.com";

        // when - 서비스의 동작을 목처리
        doNothing().when(authService).isExistingEmail(email);

        // then - 요청을 보내고 결과를 검증
        mockMvc.perform(get("/api/v1/auth/validateEmail")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"success\"}"));

        // 서비스 메서드가 호출되었는지 검증
        verify(authService).isExistingEmail(email);

    }

}