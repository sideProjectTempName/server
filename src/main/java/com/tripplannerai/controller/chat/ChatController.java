package com.tripplannerai.controller.chat;

import com.tripplannerai.annotation.Username;
import com.tripplannerai.dto.response.chat.ChatResponseDto;
import com.tripplannerai.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tripplannerai.dto.response.chat.ChatResponseDto.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private ChatService chatService;

    @PostMapping("/gathering/{gatheringId}/chat")
    public ResponseEntity<AddChatRoomResponse> addChatRoom(@RequestParam String roomName, @Username String email){
        AddChatRoomResponse addChatResponse = chatService.addChatRoom(roomName,email);
        return new ResponseEntity<>(addChatResponse, HttpStatus.OK);
    }

    @GetMapping("/messages/{chatId}")
    public ResponseEntity<ChatMessagesResponse> fetchMessages(@PathVariable Long chatId, @Username String email){
        ChatMessagesResponse chatMessagesResponse = chatService.fetchMessages(chatId,email);
        return new ResponseEntity<>(chatMessagesResponse, HttpStatus.OK);
    }

    @DeleteMapping("/chat/{chatId}")
    public ResponseEntity<LeaveChatResponse> leaveChat(@PathVariable Long chatId,@Username String email){
        LeaveChatResponse leaveChatResponse = chatService.leaveChat(chatId,email);
        return new ResponseEntity<>(leaveChatResponse, HttpStatus.OK);
    }

    @PostMapping("/gathering/{gatheringId}/chat/{chatId}/attend")
    public ResponseEntity<AttendChatResponse> attendChat(@RequestParam Long chatId, @Username String email){
        AttendChatResponse attendChatResponse = chatService.attendChat(chatId,email);
        return new ResponseEntity<>(attendChatResponse, HttpStatus.OK);
    }

    @PostMapping("/chat/{chatId}")
    public ResponseEntity<ReadChatMessageResponse> readChatMessage(@PathVariable Long chatId, @Username String email){
        ReadChatMessageResponse readChatMessageResponse = chatService.readChatMessage(chatId,email);
        return new ResponseEntity<>(readChatMessageResponse, HttpStatus.OK);
    }

    @GetMapping("/gathering/{gatheringId}/chats")
    public ResponseEntity<ChatRoomResponse> fetchChatRooms(@RequestParam Integer pageNum, @Username String email){
        ChatRoomResponse chatRoomResponse = chatService.fetchChatRooms(pageNum,email);
        return new ResponseEntity<>(chatRoomResponse, HttpStatus.OK);
    }

    @GetMapping("/gathering/{gatheringId}//my/chats")
    public ResponseEntity<MyChatRoomResponse> fetchMyChatRooms(@RequestParam Integer pageNum, @Username String email){
        MyChatRoomResponse myChatRoomResponse = chatService.fetchMyChatRooms(pageNum,email);
        return new ResponseEntity<>(myChatRoomResponse, HttpStatus.OK);
    }
}
