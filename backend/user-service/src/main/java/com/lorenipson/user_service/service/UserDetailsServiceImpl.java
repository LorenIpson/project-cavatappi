package com.lorenipson.user_service.service;

import com.lorenipson.user_service.entity.Member;
import com.lorenipson.user_service.repository.MemberRepository;
import com.lorenipson.user_service.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepos;

    public UserDetailsServiceImpl(MemberRepository memberRepos) {
        this.memberRepos = memberRepos;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepos.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new UserDetailsImpl(member);

    }

}
