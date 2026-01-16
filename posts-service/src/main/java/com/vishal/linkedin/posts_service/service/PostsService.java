package com.vishal.linkedin.posts_service.service;

import com.vishal.linkedin.posts_service.auth.UserContextHolder;
import com.vishal.linkedin.posts_service.clients.ConnectionsClient;
import com.vishal.linkedin.posts_service.dto.PersonDto;
import com.vishal.linkedin.posts_service.dto.PostCreateRequestDto;
import com.vishal.linkedin.posts_service.dto.PostDto;
import com.vishal.linkedin.posts_service.entity.Post;
import com.vishal.linkedin.posts_service.exception.ResourceNotFoundException;
import com.vishal.linkedin.posts_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsClient connectionsClient;

    public PostDto createPost(PostCreateRequestDto postDto, Long userId) {
        Post post = modelMapper.map(postDto, Post.class);
        post.setUserId(userId);

        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        log.debug("Retrieving post with id: {}", postId);

        Long userId = UserContextHolder.getCurrentUserId();

        List<PersonDto> firstConnections = connectionsClient.getFirstConnections();

//        TODO send Notification to all Connections

        Post post =  postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post not found with id: "+ postId));

        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostOfUser(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);

        return posts
                .stream()
                .map((element) -> modelMapper.map(element, PostDto.class))
                .toList();
    }
}
