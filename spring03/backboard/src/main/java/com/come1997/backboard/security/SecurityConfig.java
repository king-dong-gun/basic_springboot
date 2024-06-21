package com.come1997.backboard.security;

// 🏷️스프링 시큐리티 핵심 파일

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)    // @PreAuthorize 사용설정 어노테이션
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http://localhost:8080에 로그인을 하지않고도 접근할 수 있는 권한을 주겠다!!
        http.authorizeHttpRequests((atr) -> atr.requestMatchers(new AntPathRequestMatcher("/**"))
                        .permitAll())
                // 로그인, 회원가입 페이지만 접근 가능
                // http.authorizeHttpRequests((atr) -> atr.requestMatchers(new AntPathRequestMatcher("/member/registar"), new AntPathRequestMatcher("/member/login")).permitAll())
                // CSRF 위변조 공격을 막는 부분 해제, 특정 URL은 csrf 공격 리스트에서 제거
                .csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                // h2-console 페이지가 frameset, frame으로 구성 CORS와 유사한 옵션을 추가
                .headers((headers) -> headers.addHeaderWriter((new XFrameOptionsHeaderWriter(
                        // ignoringRequestMatchers 영역에 있는 프레임을 해제
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))))
                // 로그인 관련된 url 지정 ~/member.login
                // 로그인이 성공하면 루트로 변경
                .formLogin((fl) -> fl.loginPage("/member/login").defaultSuccessUrl("/"))

                // 로그아웃 처리
                .logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")).logoutSuccessUrl("/").invalidateHttpSession(true));

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 암호화 빈으로 생성
    }
}
