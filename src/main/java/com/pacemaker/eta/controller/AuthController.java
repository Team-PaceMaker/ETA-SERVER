package com.pacemaker.eta.controller;

import com.pacemaker.eta.jwt.TokenDto;
import com.pacemaker.eta.service.AuthService;
import dto.request.KakaoLoginRequestDto;
import dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/kakao")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenDto kakaoLogin(@RequestBody KakaoLoginRequestDto kakaoLoginRequestDto) {
        return authService.createToken(authService.kakaoLogin(kakaoLoginRequestDto));
    }

    @PostMapping("/logout")
    @Secured({"ROLE_USER"})
    public ResponseEntity<String> kakaoLogout(final Authentication authentication) {
        authService.logout(authentication);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    @GetMapping("/info")
    @Secured({"ROLE_USER"})
    public ResponseEntity<MemberResponseDto> getUserInfoByToken(@RequestHeader(name = "Authorization") String authorizationHeader) {
        String accessToken = extractAccessToken(authorizationHeader);
        return ResponseEntity.ok(authService.getByAccessToken(accessToken));
    }
    private String extractAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new IllegalArgumentException("Invalid access token");
    }
}
