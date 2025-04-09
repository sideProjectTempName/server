package com.tripplannerai.repository.chat;

import com.tripplannerai.dto.response.chat.ChatMessageElement;
import com.tripplannerai.entity.chat.ChatMessage;
import com.tripplannerai.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findChatMessageByChatRoom(ChatRoom chatRoom);

    @Query("select new com.tripplannerai.dto.response.chat." +
            "ChatMessageElement(cm.chatRoom.id,cm.content,p.user.username,r.status) from ChatMessage cm " +
            "left join cm.chatParticipant p " +
            "left join ReadStatus r on r.chatMessage.id = cm.id and r.chatParticipant.id = :chatParticipantId " +
            "left join ChatRoom cr on cr.id=cm.chatRoom.id " +
            "where cr.id= :roomId " +
            "order by case when r.status = true then 0 else 1 end")
    List<ChatMessageElement> fetchMessages(Long roomId, Long chatParticipantId);

}
