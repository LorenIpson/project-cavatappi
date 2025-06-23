package com.lorenipson.gateway_service.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // user-service
                        .requestMatchers("/api/user/login/**").permitAll()
                        .requestMatchers("/api/user/register/**").permitAll()
                        .requestMatchers("/api/user/profile/**").permitAll()

                        // user-service-oauth
                        .requestMatchers("/api/user/oauth/authorization/github/**").permitAll()
                        .requestMatchers("/oauth2/authorization/**").permitAll()
                        .requestMatchers("/login/oauth2/**").permitAll()

                        // menu-service
                        .requestMatchers("/api/menu/pizza/get/**").permitAll()
                        .requestMatchers("/api/menu/pizza/create/**").permitAll()
                        .requestMatchers("/proxy/api/**").permitAll()

                        // order-service
                        .requestMatchers("/api/order/place-new-order/**").permitAll()
                        .requestMatchers("/api/order/payment/line-pay/confirm/**").permitAll()
                        .requestMatchers("/api/order/my-order/**").permitAll()

                        // order-service-cart
                        .requestMatchers("/api/order/cart/preview/**").permitAll()

                        // order-service-status
                        .requestMatchers("/api/admin/order/get/**").permitAll()
                        .requestMatchers("/api/admin/order/*/**").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public JwtUtils jwtUtils(@Value("${jwt.secret-key}") String secretKeyStr) {
        return new JwtUtils(secretKeyStr);
    }

}
