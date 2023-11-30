package com.pacemaker.eta.global.config.security;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
            .formLogin().disable() // 기본 로그인 페이지 없애기
            .authorizeHttpRequests()
            .anyRequest().permitAll()
            .and().build();
    }


//            .authorizeHttpRequests()
//            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//            // .requestMatchers("/api/**").authenticated()
//            .anyRequest().permitAll()
//            .and()
//            .addFilterBefore(new JwtAuthFilter(tokenService),
//                UsernamePasswordAuthenticationFilter.class)
//
//
//            .oauth2Login(oauth2 -> oauth2
//                .successHandler())
//            .userInfoEndpoint()
//            .userService(userService));
//            .successHandler(successHandler);
//            // .failureHandler(failureHandler)
//
//
//            // .addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
//
//            return http.build()

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        // corsConfig.setAllowedOriginPatterns(List.of(corsAllowedOrigins.split(",")));
        corsConfig.setAllowedMethods(List.of("HEAD","POST","GET","DELETE","PUT", "OPTIONS", "PATCH"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setExposedHeaders(List.of(HttpHeaders.LOCATION, HttpHeaders.SET_COOKIE));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //httpBasic, csrf, formLogin, rememberMe, logout, session disable
        http
            .cors()
            .and()
            .httpBasic().disable()
            .csrf().disable()
            .formLogin().disable()
            .rememberMe().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //요청에 대한 권한 설정
        http.authorizeRequests()
            .antMatchers("/oauth2/**").permitAll()
            .anyRequest().authenticated();

        //oauth2Login
//        http.oauth2Login()
//            .authorizationEndpoint().baseUri("/oauth2/authorize/google")  // 소셜 로그인 url. 기본 url은 /oauth2/authorization/{provider}
//            .authorizationRequestRepository(cookieAuthorizationRequestRepository)  // 인증 요청을 cookie 에 저장
//            .and()
//            .redirectionEndpoint().baseUri("/oauth2/callback/*")  // 소셜 인증 후 redirect url
//            .and()
//            //userService()는 OAuth2 인증 과정에서 Authentication 생성에 필요한 OAuth2User 를 반환하는 클래스를 지정한다.
//            .userInfoEndpoint().userService(customOAuth2UserService)  // 회원 정보 처리
//            .and()
//            .successHandler(oAuth2AuthenticationSuccessHandler)
//            .failureHandler(oAuth2AuthenticationFailureHandler);

        http.logout()
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID");

        //jwt filter 설정
        // http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
