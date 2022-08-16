package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.UserResponseDto;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.repository.PostRepository;
import com.sparta.miniproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final PostRepository postRepository;

    @GetMapping("/myPage/{username}")
    public List<Post> myPage(@PathVariable String username) {
        List<Post> DB_List = postRepository.findAllByAuthor(username);
        List<Post> list = new ArrayList<>();

        if(DB_List.size() > 3) {
            list.add(DB_List.get(DB_List.size()-1));
            list.add(DB_List.get(DB_List.size()-2));
            list.add(DB_List.get(DB_List.size()-3));

            return list;
        }
        return DB_List;
    }


}
