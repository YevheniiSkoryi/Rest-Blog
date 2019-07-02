package com.jenia.blog.config.service;

import com.jenia.blog.config.model.MyPrinciple;
import com.jenia.blog.model.User;
import com.jenia.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new UsernameNotFoundException("User with following email doesn't exist");
        }
        return new MyPrinciple(userOpt.get());
    }
}
