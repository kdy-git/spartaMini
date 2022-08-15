package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.LoginDto;
import com.sparta.miniproject.dto.TokenDto;
import com.sparta.miniproject.dto.UserDto;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/idCheck")
    public boolean idCheck(@RequestBody UserDto userDto) {
        return userRepository.existsByUsername(userDto.getUsername());
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }

}
