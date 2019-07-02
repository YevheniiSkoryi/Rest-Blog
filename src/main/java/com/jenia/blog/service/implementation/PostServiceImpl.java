package com.jenia.blog.service.implementation;

import com.jenia.blog.dto.PostDTOIn;
import com.jenia.blog.model.Post;
import com.jenia.blog.model.User;
import com.jenia.blog.repository.PostRepository;
import com.jenia.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findAllUserPosts(User user) {
        return postRepository.findByUser(user);
    }

    @Override
    public Optional<Post> findById(Integer id) {
        return postRepository.findById(id);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public Post edit(final Post post, final PostDTOIn postIn) {
        post.merge(postIn);
        save(post);
        return post;
    }
}
