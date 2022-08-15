package com.sparta.miniproject.config;

import com.sparta.miniproject.util.StringToTargetTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Converter를 추가하기 위한 설정 클래스
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    // 구현한 StringToTargetTypeConverter를 스프링에 사용하겠다고 알리는 역할
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToTargetTypeConverter());
    }
}
