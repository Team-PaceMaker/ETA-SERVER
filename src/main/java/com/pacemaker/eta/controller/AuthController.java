package com.pacemaker.eta.controller;

import com.pacemaker.eta.service.AuthService;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/oauth2/code/google")
    public Map<String, Object> oauthGoogleCheck(@RequestParam(value = "code") String authCode) throws Exception{
        return authService.authenticateUser(authCode);
    }

    @GetMapping("/member")
    public Authentication getCurrentUser(Authentication authentication) {
        return authentication;
    }

}
