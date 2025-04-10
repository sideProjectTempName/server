package com.tripplannerai.entity.group;

import com.tripplannerai.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;
    private String name;
    private int count;
    private int point;
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void changePoint(int point){
        this.point = this.point + point;
    }
}
