package com.lorenipson.user_service.service;

import com.lorenipson.user_service.dto.OTPVerifyRequest;
import com.lorenipson.user_service.entity.MemberVerification;
import com.lorenipson.user_service.entity.Member;
import com.lorenipson.user_service.repository.EmailVerificationRepository;
import com.lorenipson.user_service.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class OTPService {

    private final EmailVerificationRepository emailVerificationRepos;
    private final MemberRepository memberRepos;

    private final JavaMailSender mailSender;

    public OTPService(EmailVerificationRepository emailVerificationRepos,
                      MemberRepository memberRepos,
                      JavaMailSender mailSender) {
        this.emailVerificationRepos = emailVerificationRepos;
        this.memberRepos = memberRepos;
        this.mailSender = mailSender;
    }

    @Transactional
    public void sendVerificationMail(String address, Member member) {

        MemberVerification newMail = new MemberVerification();

        LocalDateTime now = LocalDateTime.now();
        int otp = new Random().nextInt(110000, 990000);
        UUID uuid = UUID.randomUUID();

        newMail.setMember(member);

        newMail.setToken(uuid);
        newMail.setOtp("" + otp);
        newMail.setIsUsed(false);
        newMail.setExpiredAt(now.plusMinutes(5));
        newMail.setCreatedAt(now);
        emailVerificationRepos.save(newMail);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject("E-Mail Verification Code");
        message.setText("Your verification code : " + otp + "\nThe code expired at : " + now + "\nPlease click the link : " + "http://localhost:8080/verifyPage?token=" + uuid);
        mailSender.send(message);

    }

    @Transactional
    public boolean checkRegistrationOTPStatus(UUID token) {

        MemberVerification byToken = emailVerificationRepos
                .findByToken(token).orElseThrow(EntityNotFoundException::new);

        return !byToken.getIsUsed() && !byToken.getExpiredAt().isBefore(LocalDateTime.now());

    }

    @Transactional
    public boolean verifyRegistrationOTP(OTPVerifyRequest request) {

        MemberVerification byToken = emailVerificationRepos
                .findByToken(request.getToken()).orElseThrow(EntityNotFoundException::new);

        if (byToken.getIsUsed()) {
            throw new BadCredentialsException("Already Used");
        }

        if (byToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            // emailVerificationRepos.delete(byToken);
            throw new BadCredentialsException("Expired");
        }

        if (byToken.getOtp().equals(request.getOtp())) {
            throw new BadCredentialsException("OTP Mismatch");
        }

        Member targetMember = byToken.getMember();
        targetMember.setIsEnabled(true);
        memberRepos.save(targetMember);

        byToken.setIsUsed(true);
        emailVerificationRepos.save(byToken);
        return true;

    }

}
