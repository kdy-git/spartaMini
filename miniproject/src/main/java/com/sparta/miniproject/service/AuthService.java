package com.sparta.miniproject.service;

import com.sparta.miniproject.dto.TokenDto;
import com.sparta.miniproject.dto.TokenRequestDto;
import com.sparta.miniproject.dto.UserRequestDto;
import com.sparta.miniproject.dto.UserResponseDto;
import com.sparta.miniproject.jwt.TokenProvider;
import com.sparta.miniproject.model.RefreshToken;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.RefreshTokenRepository;
import com.sparta.miniproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Valid;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;



    @Transactional
    public UserResponseDto signup(UserRequestDto userRequestDto) {
        if (userRepository.existsByUsername(userRequestDto.getUsername())) {
            throw new IllegalArgumentException("이미 가입되어 있는 유저입니다");
        }

        User user = userRequestDto.toUser(passwordEncoder);
        return UserResponseDto.of(userRepository.save(user));
    }

    //Authentication
    //사용자가 입력한 Login ID, PW 로 인증 정보 객체 UsernamePasswordAuthenticationToken를 생성합니다.
    //아직 인증이 완료된 객체가 아니며 AuthenticationManager 에서 authenticate 메소드의 파라미터로 넘겨서 검증 후에 Authentication 를 받습니다.
    //AuthenticationManager
    //스프링 시큐리티에서 실제로 인증이 이루어지는 곳입니다.
    //authenticate 메소드 하나만 정의되어 있는 인터페이스며 위 코드에서는 Builder 에서 UserDetails 의 유저 정보가 서로 일치하는지 검사합니다.
    //그런데 코드상으로는 전혀 구현된게 없는데 어떻게 된 걸까요?
    //내부적으로 수행되는 검증 과정은 아래의 CustomUserDetailsService 클래스에서 다루겠습니다.
    //인증이 완료된 authentication 에는 Member ID 가 들어있습니다.
    //인증 객체를 바탕으로 Access Token + Refresh Token 을 생성합니다.
    //Refresh Token 은 저장하고, 생성된 토큰 정보를 클라이언트에게 전달합니다.

    @Transactional
    public TokenDto login(UserRequestDto userRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = userRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 username 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}
