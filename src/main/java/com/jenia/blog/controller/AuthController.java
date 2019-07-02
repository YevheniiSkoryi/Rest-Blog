package com.jenia.blog.controller;

import com.jenia.blog.dto.UserDTO;
import com.jenia.blog.model.User;
import com.jenia.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
public class AuthController {

    private final UserRepository userRepository;

    @Autowired
    public AuthController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public UserDTO createUser(

            @RequestBody
            final User user
    ) {
        final User savedUser = userRepository.save(user);
        return new UserDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getPassword(),
                "I show password for simplicity, use combination login/pass for basic auth in postman"
        );
    }

}
