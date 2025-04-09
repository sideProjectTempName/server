package com.tripplannerai.repository.chat;

import com.tripplannerai.dto.response.chat.ChatRoomElement;
import com.tripplannerai.dto.response.chat.MyChatRoomElement;
import com.tripplannerai.entity.chat.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("select new com.tripplannerai.dto.response.chat." +
            "ChatRoomElement(c.id, c.name, c.count, u.username, " +
            "case when cp.status = true then true else false end) " +
            "from ChatRoom c " +
            "left join c.createdBy u " +
            "left join ChatParticipant cp on cp.chatRoom.id = c.id and cp.user.id = :userId " +
            "order by case when cp.id is not null then 0 else 1 end, c.id asc")
    Page<ChatRoomElement> fetchChatRooms(Pageable pageable, Long userId);

    @Query("select new com.tripplannerai.dto.response.chat." +
            "MyChatRoomElement(c.id,c.name,c.count,cr.username,true," +
            "(select count(r.id) from ReadStatus r " +
            "left join r.chatParticipant cp left join cp.user cu " +
            "where cu.id=:userId and r.status=false)) " +
            "from ChatParticipant p " +
            "left join p.chatRoom c " +
            "left join p.user u " +
            "left join c.createdBy cr " +
            "where u.id = :userId " +
            "group by c.id")
    Page<MyChatRoomElement> fetchMyChatRooms(Pageable pageable, Long userId);

}
