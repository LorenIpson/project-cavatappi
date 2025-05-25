package com.lorenipson.user_service.security;

import com.lorenipson.user_service.service.CustomOidcUserService;
import com.lorenipson.user_service.service.OAuthSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    public SecurityConfig(CustomOidcUserService customOidcUserService, OAuthSuccessHandler oAuthSuccessHandler) {
        this.customOidcUserService = customOidcUserService;
        this.oAuthSuccessHandler = oAuthSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth2 -> oauth2
                        // TODO: OAuth 在 Authentication 階段看起來會用到 Session，之後再修改實作 cookie 版本
                        // .authorizationEndpoint()
                        // .authorizationRequestRepository(this.this.cookieAuthorizationRequestRepository())
                        // .authorizationEndpoint(auth ->
                        // auth.authorizationRequestRepository(cookieAuthorizationRequestRepository()))
                        .successHandler(oAuthSuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(customOidcUserService)))
                //.oauth2Login(Customizer.withDefaults()) // 要記得這一個設定，忘記加上浪費了我兩個小時。
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/register/**").permitAll()
                        .requestMatchers("/api/user/login/**").permitAll()

                        .requestMatchers("/api/user/oauth/authorization/github/**").permitAll()
                        .requestMatchers("/oauth2/authorization/**").permitAll()
                        .requestMatchers("/login/oauth2/**").permitAll()

                        .requestMatchers("/api/user/yolo/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        // TODO: .userEndpoint or GrantedAuthoritiesMapper
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
