package com.ireland.ager.config;

import com.ireland.ager.config.interceptor.KakaoAuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Class : KakaoAuthenticationConfig
 * @Description : 인터셉터 설정 클래스
 **/
@Configuration
@RequiredArgsConstructor
public class KakaoAuthenticationConfig implements WebMvcConfigurer {
    private final KakaoAuthenticationInterceptor kakaoAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(kakaoAuthenticationInterceptor)
                .addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
