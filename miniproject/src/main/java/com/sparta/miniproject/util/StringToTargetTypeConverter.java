package com.sparta.miniproject.util;


import com.sparta.miniproject.model.TargetType;
import org.springframework.core.convert.converter.Converter;

// 컨트롤러에서 문자열을 TargetType으로 변환해서 받을 수 있게 하는 Converter
public class StringToTargetTypeConverter implements Converter<String, TargetType> {
    @Override
    public TargetType convert(String source) {
        return TargetType.valueOf(source.toUpperCase());
    }
}