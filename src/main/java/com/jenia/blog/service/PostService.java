package com.jenia.blog.service;

import com.jenia.blog.dto.PostDTOIn;
import com.jenia.blog.model.Post;
import com.jenia.blog.model.User;

import java.util.List;
import java.util.Optional;


public interface PostService {

    List<Post> findAll();

    List<Post> findAllUserPosts(User user);

    Optional<Post> findById(Integer id);

    Post save(Post post);

    Post edit(Post post, PostDTOIn postIn);

    void delete(Post post);

}
