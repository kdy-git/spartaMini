package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String username;

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUsername());
    }

}
