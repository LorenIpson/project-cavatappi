package com.lorenipson.user_service.service;

import com.lorenipson.user_service.dto.EasyRegisterRequest;
import com.lorenipson.user_service.dto.MemberRegisterRequest;
import com.lorenipson.user_service.entity.Member;
import com.lorenipson.user_service.entity.MemberAuths;
import com.lorenipson.user_service.entity.MemberRoles;
import com.lorenipson.user_service.repository.MemberAuthsRepository;
import com.lorenipson.user_service.repository.MemberRepository;
import com.lorenipson.user_service.repository.MemberRolesRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterService {

    private final MemberRepository memberRepos;
    private final MemberAuthsRepository memberAuthsRepos;
    private final MemberRolesRepository memberRolesRepos;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(MemberRepository memberRepos,
                           MemberAuthsRepository memberAuthsRepos,
                           MemberRolesRepository memberRolesRepos,
                           PasswordEncoder passwordEncoder) {
        this.memberRepos = memberRepos;
        this.memberAuthsRepos = memberAuthsRepos;
        this.memberRolesRepos = memberRolesRepos;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 本地服務註冊。<br>
     * 可擁有其他服務如點數累積、歷史訂單、優惠等。<br>
     */
    @Transactional
    public void memberRegister(MemberRegisterRequest request) {
        Member member = new Member();

        member.setUsername(request.getUsername());
        member.setEmail(request.getEmail());
        if (request.getFirstName() != null) {
            member.setFirstName(request.getFirstName());
        }
        member.setLastName(request.getLastName());
        member.setBirthDate(request.getBirthDate());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());
        memberRepos.save(member);

        MemberAuths memberAuths = new MemberAuths();
        memberAuths.setMemberId(member);
        memberAuths.setProvider("local");
        memberAuths.setPassword(passwordEncoder.encode(request.getPassword()));
        memberAuthsRepos.save(memberAuths);

        MemberRoles memberRoles = new MemberRoles();
        memberRoles.setMemberId(member);
        memberRoles.setAuthority("USER");
        memberRolesRepos.save(memberRoles);

    }

    /**
     * 訪客註冊。<br>
     * 用於結帳快速註冊，發送訂單追蹤連結。<br>
     * 只用電話號碼簡易認證，無法作為後續登入並查看訂單。<br>
     * 號碼非 UNIQUE 約束。<br>
     */
    @Transactional
    public void easyRegister(EasyRegisterRequest request) {

        Member member = new Member();
        String uuid = UUID.randomUUID().toString();
        member.setUsername("GUEST_" + uuid);
        if (request.getFirstName() != null) {
            member.setFirstName(request.getFirstName());
        }
        member.setLastName(request.getLastName());
        member.setPhone(request.getPhone());
        if (request.getAddress() != null) {
            member.setAddress(request.getAddress());
        }
        memberRepos.save(member);

        MemberAuths memberAuths = new MemberAuths();
        memberAuths.setMemberId(member);
        memberAuths.setProvider("guest");
        memberAuthsRepos.save(memberAuths);

        MemberRoles memberRoles = new MemberRoles();
        memberRoles.setMemberId(member);
        memberRoles.setAuthority("GUEST");
        memberRolesRepos.save(memberRoles);

    }

}
