package com.tripplannerai.dto.response.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomElement {
    private Long id;
    private String name;
    private int count;
    private String email;
    private boolean status;
}
