package com.lorenipson.user_service.security;

import com.lorenipson.user_service.entity.Member;
import com.lorenipson.user_service.entity.MemberAuths;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private final Member member;

    public UserDetailsImpl(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return member.getMemberRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return member.getMemberAuths()
                .stream()
                .filter(provider -> provider.getProvider().equals("local"))
                .map(MemberAuths::getPassword)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    public int getAge() {
        if (member.getBirthDate() == null) {
            return 0;
        }
        return Period.between(member.getBirthDate(), LocalDate.now()).getYears();
    }

}
