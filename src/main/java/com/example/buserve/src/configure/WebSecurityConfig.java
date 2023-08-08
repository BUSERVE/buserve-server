package com.example.buserve.src.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/**", "/h2-console/**").permitAll() // 임시로 /api 이후의 엔드포인트에 대해 허용
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .permitAll();

        // H2 Console 사용 시 필요한 설정
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
