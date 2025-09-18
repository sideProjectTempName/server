package com.tripplannerai.entity.comment;

import com.tripplannerai.entity.BaseEntity;
import com.tripplannerai.entity.group.Group;
import com.tripplannerai.entity.member.Member;
import com.tripplannerai.entity.receiptreview.ReceiptReview;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupComment extends BaseEntity {
    @Id
    @Column(name = "group_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int count;
    private String content;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    private boolean isDeleted;
    private int depth;
    private String path;

    public static GroupComment of(Member member, Group group, String content) {
        return GroupComment.builder()
                .build();
    }
}
