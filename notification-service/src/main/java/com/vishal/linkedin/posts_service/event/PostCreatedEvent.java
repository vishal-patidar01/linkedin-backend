package com.vishal.linkedin.posts_service.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreatedEvent {

    private Long creatorId;
    private String content;
    private Long postId;
}
