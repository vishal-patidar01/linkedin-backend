package com.vishal.linkedin.posts_service.event;


import lombok.Builder;
import lombok.Data;

@Data
public class PostLikedEvent {

    private Long postId;
    private Long creatorId;
    private Long likedByUserId;
}
