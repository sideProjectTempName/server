package com.tripplannerai.entity.group;

import com.tripplannerai.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


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
    private String title;
    private boolean status;
    @Lob
    private String description;
    private int count;
    @Column(name = "max_count")
    private int maxCount;
    private int point;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "area_code")
    private String areaCode;
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void changePoint(int point){
        this.point = this.point + point;
    }
}
