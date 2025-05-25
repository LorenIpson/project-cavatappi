package com.lorenipson.user_service.service;

import com.lorenipson.user_service.entity.Member;
import com.lorenipson.user_service.entity.MemberAuths;
import com.lorenipson.user_service.entity.MemberRoles;
import com.lorenipson.user_service.repository.MemberAuthsRepository;
import com.lorenipson.user_service.repository.MemberRolesRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${frontend.url}")
    private String frontendUrl;

    private final MemberAuthsRepository memberAuthsRepos;
    private final MemberRolesRepository memberRolesRepos;

    private final JWTService jwtService;

    public OAuthSuccessHandler(MemberAuthsRepository memberAuthsRepos,
                               MemberRolesRepository memberRolesRepos,
                               JWTService jwtService) {
        this.memberAuthsRepos = memberAuthsRepos;
        this.memberRolesRepos = memberRolesRepos;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        String sub = oidcUser.getSubject();
        String provider = "google";
        Member member = memberAuthsRepos.findByProviderAndProviderUserId(provider, sub).map(MemberAuths::getMemberId)
                .orElseThrow(EntityNotFoundException::new);
        List<String> roles = memberRolesRepos.findByMemberId(member).stream()
                .map(MemberRoles::getAuthority)
                .map(role -> "ROLE_" + role).toList();

        String token = jwtService.createLoginAccessToken(member, roles);

        response.sendRedirect(frontendUrl + "/oauth/callback?token=" + token);

    }

}
