package com.tripplannerai.entity.chat;

import com.tripplannerai.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "chat_participant")
public class ChatParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    ChatRoom chatRoom;
    boolean status;

    public void changeStatus(boolean status) {
        this.status = status;
    }

    public static ChatParticipant of(ChatRoom chatRoom,Member member,boolean status){
        return ChatParticipant.builder()
                .chatRoom(chatRoom)
                .member(member)
                .status(status)
                .build();
    }


}
