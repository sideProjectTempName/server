package com.tripplannerai.service.chat;

import com.tripplannerai.entity.chat.ChatParticipant;
import com.tripplannerai.entity.chat.ChatRoom;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.exception.member.NotFoundMemberException;
import com.tripplannerai.repository.chat.ChatMessageRepository;
import com.tripplannerai.repository.chat.ChatParticipantRepository;
import com.tripplannerai.repository.chat.ChatRoomRepository;
import com.tripplannerai.repository.chat.ReadStatusRepository;
import com.tripplannerai.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.tripplannerai.dto.response.chat.ChatResponseDto.*;
import static com.tripplannerai.util.ConstClass.SUCCESS_CODE;
import static com.tripplannerai.util.ConstClass.SUCCESS_MESSAGE;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ReadStatusRepository readStatusRepository;

    public AddChatRoomResponse addChatRoom(String roomName, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new NotFoundMemberException("no exist Member!!"));
        ChatRoom chatRoom = ChatRoom.of(roomName, member);
        ChatParticipant chatParticipant = ChatParticipant.of(chatRoom, member,false);
        chatRoomRepository.save(chatRoom);
        chatParticipantRepository.save(chatParticipant);
        return AddChatRoomResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE, chatRoom.getId());
    }

    public LeaveChatResponse leaveChat(Long chatId, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new NotFoundMemberException("no exist Member!!"));

        ChatRoom chatRoom = chatRoomRepository.findById(chatId)
                .orElseThrow(() -> new NotFoundChatRoomException("no exist ChatRoom!!"));
        ChatParticipant chatParticipant = chatParticipantRepository.findByChatRoomAndUserAndStatus(chatRoom,user,true)
                .orElseThrow(()-> new NotFoundChatParticipantException("no exist ChatParticipant!!"));
        chatParticipant.changeStatus(false);
        return LeaveChatResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public AttendChatResponse attendChat(Long roomId, String email) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundChatRoomException("no exist ChatRoom!!"));
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new NotFoundMemberException("no exist Member!!"));

        Optional<ChatParticipant> optionalChatParticipant = chatParticipantRepository.findByChatRoomAndUserAndStatus(chatRoom,user,false);

        if(optionalChatParticipant.isPresent()) optionalChatParticipant.get().changeStatus(true);
        if(optionalChatParticipant.isEmpty()) chatParticipantRepository.save(ChatParticipant.of(chatRoom,user,true));
        chatRoom.changeCount(chatRoom.getCount()+1);
        return AttendChatResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public ChatMessagesResponse fetchMessages(Long roomId, String email) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundChatRoomException("no exist ChatRoom!!"));
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new NotFoundMemberException("no exist Member!!"));

        ChatParticipant chatParticipant = chatParticipantRepository.findByChatRoomAndUserAndStatus(chatRoom,user,true)
                .orElseThrow(()->new NotFoundChatParticipantException("no exist ChatParticipant!!"));
        Long chatParticipantId = chatParticipant.getId();
        List<ChatMessageElement> content = chatMessageRepository.fetchMessages(roomId,chatParticipantId);
        return ChatMessagesResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE, content);
    }

    public ReadChatMessageResponse readChatMessage(Long chatId, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new NotFoundMemberException("no exist Member!!"));

        ChatRoom chatRoom = chatRoomRepository.findById(chatId)
                .orElseThrow(() -> new NotFoundChatRoomException("no exist ChatRoom!!"));
        ChatParticipant chatParticipant = chatParticipantRepository.findByChatRoomAndUserAndStatus(chatRoom,user,true)
                .orElseThrow(()-> new NotFoundChatParticipantException("no exist ChatParticipant!!"));
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessageByChatRoom(chatRoom);
        Long chatParticipantId = chatParticipant.getId();
        List<Long> chatMessagesId = chatMessages.stream().map(c -> c.getId()).toList();
        readStatusRepository.readChatMessage(chatParticipantId,chatMessagesId);
        return ReadChatMessageResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE);
    }

    public ChatRoomResponse fetchChatRooms(Integer pageNum, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new NotFoundMemberException("no exist Member!!"));

        Long userId = user.getId();
        PageRequest pageRequest = PageRequest.of(pageNum, 5);
        Page<ChatRoomElement> page = chatRoomRepository.fetchChatRooms(pageRequest,userId);
        List<ChatRoomElement> content = page.getContent();
        boolean hasNext = page.hasNext();
        return ChatRoomResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE,content,hasNext);
    }

    public MyChatRoomResponse fetchMyChatRooms(Integer pageNum, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new NotFoundMemberException("no exist Member!!"));
        Long userId = user.getId();
        PageRequest pageRequest = PageRequest.of(pageNum, 5);
        Page<MyChatRoomElement> page = chatRoomRepository.fetchMyChatRooms(pageRequest,userId);
        List<MyChatRoomElement> content = page.getContent();
        boolean hasNext = page.hasNext();
        return MyChatRoomResponse.of(SUCCESS_CODE,SUCCESS_MESSAGE,content,hasNext);
    }


    public boolean isRoomParticipant(String email, long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundChatRoomException("no exist ChatRoom!!"));
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new NotFoundMemberException("no exist Member!!"));
        chatParticipantRepository.findByChatRoomAndUserAndStatus(chatRoom,user,true)
                .orElseThrow(()->new NotFoundChatParticipantException("no exist ChatParticipant!!"));
        return true;
    }

}
