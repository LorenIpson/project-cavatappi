package com.lorenipson.user_service.service;

import com.lorenipson.user_service.dto.ProfileResponse;
import com.lorenipson.user_service.entity.Member;
import com.lorenipson.user_service.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ProfileService {

    private final MemberRepository memberRepos;


    public ProfileService(MemberRepository memberRepos) {
        this.memberRepos = memberRepos;
    }


    public ProfileResponse me(UUID memberId) {

        Member member = memberRepos.findById(memberId).orElseThrow(() -> new RuntimeException("找不到目標使用者。"));

        String username = member.getUsername();
        String firstName = member.getFirstName();
        String lastName = member.getLastName();
        String email = member.getEmail();
        String phone = member.getPhone();
        String address = member.getAddress();
        LocalDate birthDate = member.getBirthDate();

        return new ProfileResponse(username, firstName, lastName, email, phone, address, birthDate);

    }

}
