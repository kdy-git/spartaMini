package com.sparta.miniproject.service;

import com.sparta.miniproject.dto.LoginDto;
import com.sparta.miniproject.dto.TokenDto;
import com.sparta.miniproject.dto.UserDto;
import com.sparta.miniproject.exception.DuplicateMemberException;
import com.sparta.miniproject.jwt.TokenProvider;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional
    public String login(LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

        System.out.println("로그인 검증");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        System.out.println("token 생성");
        String jwtToken = tokenProvider.createToken(authentication);

        return "Bearer " + jwtToken;


    }
}