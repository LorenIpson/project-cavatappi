package com.lorenipson.user_service.service;

import com.lorenipson.user_service.dto.EasyRegisterRequest;
import com.lorenipson.user_service.dto.RegisterMemberRequest;
import com.lorenipson.user_service.entity.Member;
import com.lorenipson.user_service.entity.MemberAuths;
import com.lorenipson.user_service.repository.MemberAuthsRepository;
import com.lorenipson.user_service.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final MemberRepository memberRepos;
    private final MemberAuthsRepository memberAuthsRepos;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(MemberRepository memberRepos,
                           MemberAuthsRepository memberAuthsRepos,
                           PasswordEncoder passwordEncoder) {
        this.memberRepos = memberRepos;
        this.memberAuthsRepos = memberAuthsRepos;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean register(RegisterMemberRequest request) {
        Member member = new Member();

        member.setUsername(request.getUsername());
        member.setEmail(request.getEmail());
        if (request.getFirstName() != null) {
            member.setFirstName(request.getFirstName());
        }
        member.setLastName(request.getLastName());
        member.setAge(request.getAge());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());

        member.setRole((short) 1);
        memberRepos.save(member);

        MemberAuths memberAuths = new MemberAuths();
        memberAuths.setMemberId(member.getId());
        memberAuths.setProvider("Cavatappi");
        memberAuths.setProviderId("42069");
        memberAuths.setPassword(passwordEncoder.encode(request.getPassword()));
        memberAuthsRepos.save(memberAuths);

        return true;

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
        member.setUsername("GUEST");
        if (request.getEmail() != null) {
            member.setEmail(request.getEmail());
        } else {
            member.setEmail("GUEST___@cavatappi.com");
        }
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setPhone(request.getPhone());
        if (request.getAddress() != null) {
            member.setAddress(request.getAddress());
        }
        member.setRole((short) 1); // 訪客。
        memberRepos.save(member);

        MemberAuths memberAuths = new MemberAuths();
        memberAuths.setMemberId(member.getId());
        memberAuths.setProvider("GUEST_Cavatappi");
        memberAuthsRepos.save(memberAuths);

    }

}
