package com.lorenipson.gateway_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * 解析 JWT 是否通過 Validation，並且重新包裝 Header。<br>
 * - 當不通過驗證時，回傳 UNAUTHORIZED。<br>
 * - 使用 Servlet 的 RequestWrapper 將解析後的 sub 與 X-username 資訊加入 Header，使後端微服務可以支援使用 @RequestHeader。
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Get Header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(BEARER_PREFIX.length());
        Claims claims;

        try {
            claims = jwtUtils.parseToken(jwt);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String memberId = claims.getSubject();
        String username = claims.get("username", String.class);
        System.out.println("GATEWAY ============================== USERNAME: " + username);

        HeaderRequestWrapper wrapped = new HeaderRequestWrapper(request);
        wrapped.addHeader("X-Member-Id", memberId);
        wrapped.addHeader("X-Username", username);

        System.out.println("=================================================== nah");

        if (!jwtUtils.validateToken(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        List<?> roles = claims.get("authorities", List.class);
        List<String> rolesStr = roles.stream().map(String::valueOf).toList();
        List<SimpleGrantedAuthority> authorities = rolesStr.stream().map(SimpleGrantedAuthority::new).toList();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(wrapped, response);

    }

}
