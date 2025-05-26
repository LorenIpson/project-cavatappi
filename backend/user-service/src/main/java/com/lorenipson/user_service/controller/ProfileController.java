package com.lorenipson.user_service.controller;

import com.lorenipson.user_service.dto.ProfileResponse;
import com.lorenipson.user_service.security.UserDetailsImpl;
import com.lorenipson.user_service.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/api/user/profile/me")
    public ResponseEntity<ProfileResponse> me(Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UUID uuid = userDetails.getUUID();
        ProfileResponse response = profileService.me(uuid);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/api/user/profile/me2")
    public ResponseEntity<String> me2() {
        return ResponseEntity.ok("me2");
    }

}
