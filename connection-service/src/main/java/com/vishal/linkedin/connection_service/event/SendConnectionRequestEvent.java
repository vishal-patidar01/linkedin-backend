package com.vishal.linkedin.connection_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendConnectionRequestEvent {

    private Long senderId;
    private Long receiverId;
}
