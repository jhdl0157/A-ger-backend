package com.ireland.ager.config.interceptor;

import com.ireland.ager.account.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Class : KakaoAuthenticationInterceptor
 * @Description : 카카오 인증 인터셉터
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoAuthenticationInterceptor implements HandlerInterceptor {
    private final AuthServiceImpl authService;
    private static final String[] excludeList = {
            "/api/account/login-url"
            , "/api/account/login"
            , "/api/account/token/**"
            , "/favicon.ico/**"
            , "/favicon.ico"
            , "/kafka/*"
            , "/kafka/**"
            , "/socket.io/*"
            , "/api/popular-keyword"
    };

    /**
     * @Method : preHandle implements HandlerInterceptor
     * @Description : 토큰 유효성 검사
     * @Parameter : [request, response, handler]
     * @Return : boolean
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String requestUrl = request.getRequestURI();
        log.info("URL:{}", requestUrl);
        if (!PatternMatchUtils.simpleMatch(excludeList, requestUrl)) {
            log.info("Not Match");
            authService.isValidToken(token);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
