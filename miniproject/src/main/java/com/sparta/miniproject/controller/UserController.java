package com.sparta.miniproject.controller;
import com.sparta.miniproject.dto.UserResponseDto;
import com.sparta.miniproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyMemberInfo() {
        return ResponseEntity.ok(userService.getMyInfo());
    }


    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> getMemberInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getMemberInfo(username));
    }



}
