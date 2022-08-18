package com.sparta.miniproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답을 할때 json을 자바스크립트에서 처리할 수 있게 할지를 설정하는것. false이면 자바스크립트에서 서버로 응답을 보내도 응답하지 않음
        config.addAllowedOriginPattern("*"); // 모든 ip의 응답을 허용
        config.addAllowedHeader("*"); // 모든 Header에 응답을 허용
        config.addAllowedMethod("*");// 모든 메서드 (post,get,put,delete,patch) 에 응답을 허용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
