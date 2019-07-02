package com.jenia.blog.controller;

import com.jenia.blog.dto.PostDTOIn;
import com.jenia.blog.dto.PostDTOOut;
import com.jenia.blog.exception.NoAuthorityException;
import com.jenia.blog.exception.ResourceNotFoundException;
import com.jenia.blog.model.Post;
import com.jenia.blog.model.User;
import com.jenia.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostDTOOut> getPosts(

            @AuthenticationPrincipal(expression = "user")
            final User user,

            @RequestParam(defaultValue = "false")
            final Boolean own
    ) {
        final List<Post> posts = new ArrayList<>();
        if (own) {
            posts.addAll(postService.findAllUserPosts(user));
        } else {
            posts.addAll(postService.findAll());
        }
        return posts.stream()
                .map(post -> new PostDTOOut(post.getId(), post.getTitle(), post.getBody()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public PostDTOOut createPost(

            @AuthenticationPrincipal(expression = "user")
            final User user,

            @Valid
            @NotNull
            @RequestBody
            final PostDTOIn postIn
    ) {
        final Post post = postService.save(new Post(postIn.getBody(), postIn.getTitle(), user));
        return new PostDTOOut(post.getId(), post.getTitle(), post.getBody());
    }


    @PutMapping("/{id}")
    public PostDTOOut editPost(

            @AuthenticationPrincipal(expression = "user")
            final User user,

            @PathVariable
            @NotNull
            final Integer id,

            @Valid
            @NotNull
            @RequestBody
            final PostDTOIn postIn

    ) {
        final Optional<Post> optionalPost = postService.findById(id);

        if (optionalPost.isPresent()) {
            final Post post = optionalPost.get();

            if (user.getId().equals(post.getUser().getId())) {
                postService.edit(post, postIn);
                return new PostDTOOut(
                        post.getId(),
                        post.getTitle(),
                        post.getBody()
                );
            } else {
                throw new NoAuthorityException();
            }
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("/{id}")
    public PostDTOOut getPostWithId(

            @PathVariable
            @NotNull
            final Integer id
    ) {

        final Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isPresent()) {
            final Post post = optionalPost.get();
            return new PostDTOOut(
                    post.getId(),
                    post.getTitle(),
                    post.getBody()
            );
        }
        throw new ResourceNotFoundException();
    }

    @DeleteMapping("/{id}")
    public void deletePost(
            @AuthenticationPrincipal(expression = "user")
            final User user,

            @PathVariable
            @NotNull
            final Integer id
    ) {

        final Optional<Post> optionalPost = postService.findById(id);

        if (optionalPost.isPresent()) {
            final Post post = optionalPost.get();
            if (post.getUser().getId().equals(user.getId())) {
                postService.delete(post);
            } else {
                throw new NoAuthorityException();
            }
        } else {
            throw new ResourceNotFoundException();
        }
    }


}
