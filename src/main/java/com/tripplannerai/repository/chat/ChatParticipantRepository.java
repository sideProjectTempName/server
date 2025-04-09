package com.tripplannerai.repository.chat;

import com.tripplannerai.entity.chat.ChatParticipant;
import com.tripplannerai.entity.chat.ChatRoom;
import com.tripplannerai.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    Optional<ChatParticipant> findByChatRoomAndMemberAndStatus(ChatRoom chatRoom, Member member, boolean status);
    List<ChatParticipant> findAllByChatRoomAndStatus(ChatRoom chatRoom, boolean status);
}
