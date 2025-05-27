package com.lorenipson.user_service.service;

import com.lorenipson.user_service.dto.ProfileRequest;
import com.lorenipson.user_service.dto.ProfileResponse;
import com.lorenipson.user_service.entity.Member;
import com.lorenipson.user_service.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
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

        Member member = memberRepos.findById(memberId).orElseThrow(() -> new EntityNotFoundException("找不到目標使用者。"));

        String username = member.getUsername();
        String firstName = member.getFirstName();
        String lastName = member.getLastName();
        String email = member.getEmail();
        String phone = member.getPhone();
        String address = member.getAddress();
        LocalDate birthDate = member.getBirthDate();

        return new ProfileResponse(username, firstName, lastName, email, phone, address, birthDate);

    }

    public void editProfile(UUID memberId, ProfileRequest request) {

        Member targetMember = memberRepos.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("找不到目標使用者。"));

        targetMember.setFirstName(request.getFirstName());
        targetMember.setLastName(request.getLastName());
        targetMember.setPhone(request.getPhone());
        targetMember.setAddress(request.getAddress());
        targetMember.setBirthDate(request.getBirthDate());
        memberRepos.save(targetMember);

    }

}
