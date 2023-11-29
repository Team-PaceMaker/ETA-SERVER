package com.pacemaker.eta.auth.controller;

import com.pacemaker.eta.auth.jwt.RefreshTokenSupport;
import com.pacemaker.eta.auth.service.AuthService;
import dto.response.ReIssueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/accessToken")
    public ResponseEntity<ReIssueResponse> reIssueToken(@CookieValue(name = "refreshToken") String refreshToken) {
        ReIssueResponse response = authService.reIssueTokens(refreshToken);
        ResponseCookie cookie = RefreshTokenSupport.convertToCookie(response.refreshToken());
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(response);
    }

    @DeleteMapping("/refreshToken")
    public ResponseEntity<Void> logout(@CookieValue("refreshToken") String refreshToken) {
        authService.logoutRefreshToken(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
