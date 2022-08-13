package com.sparta.miniproject.service;

import com.sparta.miniproject.dto.UserDto;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void register(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        String email = userDto.getEmail();

        User user = new User(username, password, email);
        userRepository.save(user);
    }

}
