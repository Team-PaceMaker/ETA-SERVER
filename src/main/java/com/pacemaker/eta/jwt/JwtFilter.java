package com.pacemaker.eta.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if(request.getServletPath().startsWith("/api/v1/auth/kakao/login")) {
            filterChain.doFilter(request,response);
        }
        else {
            String token = resolveToken(request);

            log.debug("token  = {}",token);
            if(StringUtils.hasText(token)) {
                int flag = tokenProvider.validateToken(token);

                log.debug("flag = {}",flag);

                if(flag == 1) {
                    this.setAuthentication(token);
                }else if(flag == 2) { // 토큰 만료
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setCharacterEncoding("UTF-8");
                    log.debug("doFilterInternal Exception CALL!");
                    log.info("{\"error\": \"ACCESS_TOKEN_EXPIRED\", \"message\" : \"엑세스토큰이 만료되었습니다.\"}");
                } else {
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setCharacterEncoding("UTF-8");
                    log.debug("doFilterInternal Exception CALL!");
                    log.debug("{\"error\": \"BAD_TOKEN\", \"message\" : \"잘못된 토큰 값입니다.\"}");
                }
            }
            else {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setCharacterEncoding("UTF-8");
                log.debug("doFilterInternal Exception CALL!");
                log.debug("{\"error\": \"EMPTY_TOKEN\", \"message\" : \"토큰 값이 비어있습니다.\"}");
            }
            filterChain.doFilter(request, response);

        }
    }
    private void setAuthentication(String token) {
        Authentication authentication = tokenProvider.getAuthenticationByKakaoId(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
