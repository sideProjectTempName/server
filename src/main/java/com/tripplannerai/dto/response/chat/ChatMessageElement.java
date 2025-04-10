package com.tripplannerai.dto.response.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageElement {

    private Long roomId;
    private String content;
    private String email;
    private boolean status;
}
