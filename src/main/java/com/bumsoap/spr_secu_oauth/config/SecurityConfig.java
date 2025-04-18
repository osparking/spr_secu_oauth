package com.bumsoap.spr_secu_oauth.config;

import com.bumsoap.spr_secu_oauth.service.PrincipalOauthUserService;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private PrincipalOauthUserService principalOauthUserService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(cnf -> cnf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/manager/**").hasAuthority("MANAGER")
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().permitAll())
                .formLogin(folo -> folo
                        .loginPage("http://localhost:5173/login")
                        /**
                         * login 주소가 호출되면 시큐리티가 낚아 채서(post로 오는것)
                         * 대신 로그인 진행 -> 컨트롤러를 안만들어도 된다.
                         */
                        .loginProcessingUrl("http://localhost:5173")
                        .defaultSuccessUrl("http://localhost:5173"))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("http://localhost:5173/login")
                        .defaultSuccessUrl("http://localhost:5173", true)
                        .userInfoEndpoint(usrInfo -> usrInfo
                                /**
                                 * 구글 (회원)로그인된 뒤, 후속 처리 필요 -
                                 * Tip. 코드 X, (엑세스 토큰 + 사용자 프로필 정보를 받아옴)
                                 */
                                .userService(principalOauthUserService)));
        return http.build();
    }
}
