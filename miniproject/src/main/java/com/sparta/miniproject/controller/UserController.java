package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.UserDto;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/register")
    private void register(@RequestBody UserDto userDto) {
        userService.register(userDto);
    }

    @PostMapping("/idCheck")
    public boolean idCheck(@RequestBody UserDto userDto) {
        return userRepository.existsByUsername(userDto.getUsername());
    }

    @PostMapping("/emailCheck")
    public boolean emailCheck(@RequestBody UserDto userDto) {
        return userRepository.existsByEmail(userDto.getEmail());
    }

}
