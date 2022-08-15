package com.sparta.miniproject.service;


import com.sparta.miniproject.dto.UserResponseDto;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDto getMemberInfo(String username) {
        return userRepository.findByUsername(username)
                .map(UserResponseDto::of)
                .orElseThrow(
                        () -> new RuntimeException("유저 정보가 없습니다")
                );
    }

    //현재 SecurityContext에 있는 유저 정보 가져오기기
    @Transactional
    public UserResponseDto getMyInfo() {
        return userRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(UserResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }
}
