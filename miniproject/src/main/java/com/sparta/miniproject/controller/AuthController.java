package com.sparta.miniproject.controller;


import com.sparta.miniproject.dto.TokenDto;
import com.sparta.miniproject.dto.TokenRequestDto;
import com.sparta.miniproject.dto.UserRequestDto;
import com.sparta.miniproject.dto.UserResponseDto;
import com.sparta.miniproject.exception.RestApiException;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto userRequestDto) {
        try {
            return ResponseEntity.ok(authService.signup(userRequestDto));
        } catch (IllegalArgumentException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.CONFLICT);
            restApiException.setErrorMessage(ex.getMessage());
            return new ResponseEntity(restApiException, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.login(userRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @PostMapping("/idCheck")
    public boolean idCheck(@RequestBody UserRequestDto userRequestDto) {
        return userRepository.existsByUsername(userRequestDto.getUsername());
    }

    @GetMapping("/logout")
    public void logOut() {
        authService.logOut();
    }
}
