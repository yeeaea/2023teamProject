package com.pet.security.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()
                .authorizeHttpRequests(request  -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/**", "/questions","/status", "/img/**", "/join", "/auth/join").permitAll() // 누구나 접근 가능한 페이지
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")  // 커스텀 로그인 페이지 지정
                        .loginProcessingUrl("/login-process")  // submit 받을 url
                        .usernameParameter("userid")  // submit할 아이디
                        .passwordParameter("password")  // submit할 비밀번호
                        .defaultSuccessUrl("/dashboard", true)  // 로그인 성공 시 이동하는 페이지
                        .permitAll()
                )
                .logout(withDefaults());

        return http.build();
    }
}