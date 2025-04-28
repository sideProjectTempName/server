package com.tripplannerai.mapper;

import com.tripplannerai.entity.chat.ChatMessage;
import com.tripplannerai.entity.chat.ChatParticipant;
import com.tripplannerai.entity.chat.ChatRoom;

public class ChatFactory {

    public static ChatMessage from(ChatRoom chatRoom, ChatParticipant chatParticipant,String content) {
        return ChatMessage.builder()
                .chatParticipant(chatParticipant)
                .chatRoom(chatRoom)
                .content(content)
                .build();
    }
}
