package com.vishal.linkedin.posts_service.controller;

import com.vishal.linkedin.posts_service.dto.PostCreateRequestDto;
import com.vishal.linkedin.posts_service.dto.PostDto;
import com.vishal.linkedin.posts_service.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostsController {

    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postDto) {
        PostDto createdPost = postsService.createPost(postDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        PostDto postDto = postsService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostOfUser(@PathVariable Long userId) {
        List<PostDto> postDto = postsService.getAllPostOfUser(userId);
        return ResponseEntity.ok(postDto);
    }

}
