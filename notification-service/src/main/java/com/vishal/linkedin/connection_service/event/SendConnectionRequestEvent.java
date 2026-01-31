package com.vishal.linkedin.connection_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SendConnectionRequestEvent {

    private Long senderId;
    private Long receiverId;
}
