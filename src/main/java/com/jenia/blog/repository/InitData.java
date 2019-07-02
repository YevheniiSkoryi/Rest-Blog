package com.jenia.blog.repository;

import com.jenia.blog.model.Post;
import com.jenia.blog.model.Role;
import com.jenia.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Autowired
    public InitData(
            final UserRepository userRepository,
            final PostRepository postRepository
    ) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void run(final String... args) {
        final User u = new User(
                "zhenya",
                "Skoryi",
                "12345",
                "123@gmail.com"
        );
        userRepository.save(u);

        final User u1 = new User(
                "sanya",
                "Skoryi",
                "12345",
                "sanya@gmail.com"
        );

        final Role role = new Role();
        role.setRole("PUBLISHER");
        u.addRole(role);
        userRepository.save(u);
        userRepository.save(u1);

        final Post p1 = new Post(
                "My Own Post",
                "Own desc",
                u
        );

        final Post p2 = new Post(
                "Not my post",
                "Not my desc",
                u1
        );
        postRepository.save(p1);
        postRepository.save(p2);
    }
}
