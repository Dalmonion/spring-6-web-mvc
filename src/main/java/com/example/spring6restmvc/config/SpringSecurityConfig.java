package com.example.spring6restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/", "/api/v1/**").permitAll());
        return http.build();
    }

//    @Bean
//    @Order(2)
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(Customizer.withDefaults())
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/auth/**")
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .authorizeHttpRequests(authorize -> {
//                    authorize.anyRequest().permitAll();
//                })
//
//                .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> {
//                    httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults());
//                });

//        http.authorizeHttpRequests(authorize -> {
//            authorize.anyRequest().permitAll();
//        })
//                .csrf(AbstractHttpConfigurer::disable);
//
//        return http.build();
//    }
}
