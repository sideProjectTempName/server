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
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member createdBy;
    private int count;
    public void changeCount(int count){
        this.count = count;
    }
    public static ChatRoom of(String name,Member member){
        return ChatRoom.builder()
                .name(name)
                .createdBy(member)
                .count(1)
                .build();
    }

}
