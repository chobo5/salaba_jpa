package salaba.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import salaba.domain.auth.security.jwt.exception.CustomAuthenticationEntryPoint;
import salaba.domain.auth.constant.RoleName;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

// Spring Security 설정.
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManagerConfig authenticationManagerConfig;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private static final String BASE_URL = "/api/v1/";
    private static final String ADMIN = RoleName.ADMIN.name();
    private static final String MANAGER = RoleName.MANAGER.name();
    private static final String MEMBER = RoleName.MEMBER.name();


    // 가전제품 사용하는 것처럼 Spring Security라는 제품을 사용하는 것.
    // JWT토큰을 인증을 한다. 그래서 인증에서 HttpSession을 사용하지 않는다.
    // JWT의 장점을 찾아보자.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable() // 직접 id, password를 입력받아서 JWT토큰을 리턴하는 API를 직접 만든다.
                .csrf().disable() // CSRF는 Cross Site Request Forgery의 약자. CSRF공격을 막기 위한 방법.
                .cors().disable() //.configurationSource(corsConfigurationSource())
                .apply(authenticationManagerConfig)
                .and()
                .httpBasic().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // Preflight 요청은 허용한다.
                .mvcMatchers(GET, "/swagger-ui/**").permitAll()
                //auth
                .mvcMatchers(POST, BASE_URL +"auth/join", BASE_URL +"auth/login").permitAll()
                .mvcMatchers(GET,  BASE_URL +"auth/validateEmail", BASE_URL + "auth/validateNickname").permitAll()
                .mvcMatchers(PUT, BASE_URL +"auth/changeNickname", BASE_URL +"auth/changePassword", BASE_URL +"auth/changeTelNo").hasAnyAuthority(MEMBER, MANAGER, ADMIN)
                .mvcMatchers(DELETE, BASE_URL +"auth/resign").hasAnyAuthority(MEMBER, MANAGER, ADMIN)
                //member
                .mvcMatchers(BASE_URL+"member/**").hasAnyAuthority(MEMBER, MANAGER, ADMIN)
                //board
                .mvcMatchers(GET, BASE_URL+"board/**", BASE_URL+"board/list").permitAll()
                .mvcMatchers(POST, BASE_URL+"board/like", BASE_URL+"board/new").hasAnyAuthority(MEMBER, MANAGER, ADMIN)
                .mvcMatchers(PUT, BASE_URL+"board/modify").hasAnyAuthority(MEMBER, MANAGER, ADMIN)
                .mvcMatchers(DELETE, BASE_URL+"board/delete", BASE_URL+"board/cancelLike").hasAnyAuthority(MEMBER, MANAGER, ADMIN)
                //reply
                .mvcMatchers(BASE_URL+"reply/**").hasAnyAuthority(MEMBER, MANAGER, ADMIN)
                //payment
                .mvcMatchers(BASE_URL+"payment/**").hasAnyAuthority(MEMBER, MANAGER, ADMIN)
                //nation
                .mvcMatchers(BASE_URL+"nation/**").hasAnyAuthority(MEMBER, MANAGER, ADMIN)
                //reservation
                .mvcMatchers(BASE_URL+"reservation/**").hasAnyAuthority(MEMBER, MANAGER, ADMIN)
                //host
                .mvcMatchers(BASE_URL+"host/**").hasAnyAuthority(MEMBER, MANAGER, ADMIN)
                //rentalHome
                .mvcMatchers(BASE_URL+"rentalHome/detail/**").permitAll()
                .mvcMatchers(BASE_URL+"rentalHome/search/**").permitAll()
                .mvcMatchers(BASE_URL+"rentalHome/reviews/**").permitAll()
                .mvcMatchers(BASE_URL+"rentalHome/**").hasAnyAuthority(MEMBER, MANAGER, ADMIN)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .build();
    }


    // <<Advanced>> Security Cors로 변경 시도
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // config.setAllowCredentials(true); // 이거 빼면 된다
        // https://gareen.tistory.com/66
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.setAllowedMethods(List.of("GET","POST","DELETE","PATCH","OPTION","PUT"));
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    // 암호를 암호화하거나, 사용자가 입력한 암호가 기존 암호랑 일치하는지 검사할 때 이 Bean을 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

/*
BCrypt는 비밀번호를 안전하게 저장하기 위한 해시 함수입니다. BCrypt는 비밀번호 해싱을 위해 Blowfish 암호화 알고리즘을 사용하며, 암호화된 비밀번호를 저장할 때 임의의 솔트(salt)를 생성하여 비밀번호의 보안성을 높입니다.

BCrypt는 강력한 암호화 알고리즘을 사용하기 때문에 해독이 거의 불가능합니다. 이는 해커가 데이터베이스를 공격하여 해시된 비밀번호를 복원하는 것을 어렵게 만듭니다. 또한, BCrypt는 더 높은 수준의 보안성을 위해 비밀번호를 반복해서 해싱하는 기능(최소 10회 이상)을 지원합니다.

BCrypt는 Java, Ruby, Python, C#, PHP 등 다양한 프로그래밍 언어에서 사용할 수 있으며, 많은 웹 프레임워크에서 기본적으로 BCrypt를 지원하고 있습니다. 비밀번호를 안전하게 저장하기 위해서는 BCrypt와 같은 안전한 해시 함수를 사용하는 것이 좋습니다.
 */