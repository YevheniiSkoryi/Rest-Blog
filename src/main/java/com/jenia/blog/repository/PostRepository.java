package com.jenia.blog.repository;

import com.jenia.blog.model.Post;
import com.jenia.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User u);
}
