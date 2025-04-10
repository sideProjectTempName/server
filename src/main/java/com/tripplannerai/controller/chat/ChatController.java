package com.tripplannerai.controller.chat;

import com.tripplannerai.annotation.Id;
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
    public ResponseEntity<AddChatRoomResponse> addChatRoom(@RequestParam String roomName, @Id Long id){
        AddChatRoomResponse addChatResponse = chatService.addChatRoom(roomName,id);
        return new ResponseEntity<>(addChatResponse, HttpStatus.OK);
    }

    @GetMapping("/messages/{chatId}")
    public ResponseEntity<ChatMessagesResponse> fetchMessages(@PathVariable Long chatId, @Id Long id){
        ChatMessagesResponse chatMessagesResponse = chatService.fetchMessages(chatId,id);
        return new ResponseEntity<>(chatMessagesResponse, HttpStatus.OK);
    }

    @DeleteMapping("/chat/{chatId}")
    public ResponseEntity<LeaveChatResponse> leaveChat(@PathVariable Long chatId,@Id Long id){
        LeaveChatResponse leaveChatResponse = chatService.leaveChat(chatId,id);
        return new ResponseEntity<>(leaveChatResponse, HttpStatus.OK);
    }

    @PostMapping("/gathering/{gatheringId}/chat/{chatId}/attend")
    public ResponseEntity<AttendChatResponse> attendChat(@RequestParam Long chatId, @Id Long id){
        AttendChatResponse attendChatResponse = chatService.attendChat(chatId,id);
        return new ResponseEntity<>(attendChatResponse, HttpStatus.OK);
    }

    @PostMapping("/chat/{chatId}")
    public ResponseEntity<ReadChatMessageResponse> readChatMessage(@PathVariable Long chatId, @Id Long id){
        ReadChatMessageResponse readChatMessageResponse = chatService.readChatMessage(chatId,id);
        return new ResponseEntity<>(readChatMessageResponse, HttpStatus.OK);
    }

    @GetMapping("/gathering/{gatheringId}/chats")
    public ResponseEntity<ChatRoomResponse> fetchChatRooms(@RequestParam Integer pageNum, @Id Long id){
        ChatRoomResponse chatRoomResponse = chatService.fetchChatRooms(pageNum,id);
        return new ResponseEntity<>(chatRoomResponse, HttpStatus.OK);
    }

    @GetMapping("/gathering/{gatheringId}//my/chats")
    public ResponseEntity<MyChatRoomResponse> fetchMyChatRooms(@RequestParam Integer pageNum, @Id Long id){
        MyChatRoomResponse myChatRoomResponse = chatService.fetchMyChatRooms(pageNum,id);
        return new ResponseEntity<>(myChatRoomResponse, HttpStatus.OK);
    }
}
