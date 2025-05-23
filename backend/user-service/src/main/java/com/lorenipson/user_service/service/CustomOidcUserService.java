package com.lorenipson.user_service.service;

import com.lorenipson.user_service.entity.Member;
import com.lorenipson.user_service.entity.MemberAuths;
import com.lorenipson.user_service.entity.MemberRoles;
import com.lorenipson.user_service.repository.MemberAuthsRepository;
import com.lorenipson.user_service.repository.MemberRepository;
import com.lorenipson.user_service.repository.MemberRolesRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final MemberRepository memberRepos;
    private final MemberAuthsRepository memberAuthsRepos;
    private final MemberRolesRepository memberRolesRepos;

    public CustomOidcUserService(MemberRepository memberRepos,
                                 MemberAuthsRepository memberAuthsRepos,
                                 MemberRolesRepository memberRolesRepos) {
        this.memberRepos = memberRepos;
        this.memberAuthsRepos = memberAuthsRepos;
        this.memberRolesRepos = memberRolesRepos;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("LOAD USER INIT =========================================================================");

        OidcUserService oidcUserService = new OidcUserService();
        OidcUser oidcUser = oidcUserService.loadUser(userRequest);
        System.out.println("LOAD USER STAGE 2 ======================================================================");

        String provider = userRequest.getClientRegistration().getRegistrationId(); // TODO: 檢查應該要是 Google 才對
        String subject = oidcUser.getSubject();
        String email = oidcUser.getEmail();
        String profile = oidcUser.getGivenName();
        Instant expiresAt = userRequest.getAccessToken().getExpiresAt();
        System.out.println("LOAD USER STAGE 3 ======================================================================");

        memberAuthsRepos.findByProviderAndProviderUserId(provider, subject)
                .orElseGet(() -> registerNewUser(email, provider, subject, profile, expiresAt));
        System.out.println("LOAD USER STAGE 4 ======================================================================");

        return oidcUser;
    }

    private MemberAuths registerNewUser(String email, String provider, String subject, String profile, Instant expiresAt) {

        Member member = new Member();
        member.setEmail(email);
        // 臨時，下一頁面要讓使用者更新，更新完後 is_complete = true，且部分將不能再更新。
        member.setUsername("Google-" + UUID.randomUUID());
        member.setFirstName(profile);
        member.setPhone("未提供");
        member.setAddress("未提供");
        member.setIsEnabled(true);
        member.setIsComplete(false);
        member.setIsLocked(false);
        member.setCreatedAt(LocalDateTime.now());
        Member newMember = memberRepos.save(member);
        System.out.println("MEMBER ==============================================================================");

        MemberAuths auth = new MemberAuths();
        auth.setMemberId(newMember);
        auth.setProvider(provider);
        auth.setProviderUserId(subject);
        LocalDateTime exp = LocalDateTime.ofInstant(expiresAt, ZoneId.of("Asia/Taipei"));
        auth.setTokenExpiresAt(exp);
        auth.setCreatedAt(LocalDateTime.now());
        memberAuthsRepos.save(auth);
        System.out.println("AUTH ==============================================================================");

        MemberRoles roles = new MemberRoles();
        roles.setMemberId(newMember);
        roles.setAuthority("USER");
        memberRolesRepos.save(roles);
        System.out.println("ROLE ==============================================================================");

        System.out.println("新建成功 ==============================================================================");

        return auth;
    }

}
