package com.tripplannerai.controller.stomp;

import com.tripplannerai.dto.request.chat.ChatMessageRequest;
import com.tripplannerai.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompController {
    private final SimpMessageSendingOperations messageTemplate;
    private final ChatService chatService;
    @MessageMapping("/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, @Payload ChatMessageRequest chatMessageRequest){

        chatService.save(roomId,chatMessageRequest);
        messageTemplate.convertAndSend("/subscribe/"+roomId, chatMessageRequest);

    }

}
