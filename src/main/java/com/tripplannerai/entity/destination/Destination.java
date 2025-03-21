package com.tripplannerai.entity.destination;

import com.tripplannerai.entity.address.Address;
import com.tripplannerai.entity.category.Category;
import com.tripplannerai.entity.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "destination")
@Builder
public class Destination {
    @Id
    @Column(name = "destination_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long destinationId;
    private String name;
    private String addr1; // 주소
    private String addr2; // 상세주소
    private String tel; //전화번호
    private String contentId; // Tourapi에서 제공하는 아이디

    private String latitude; //위도
    private String longitude; //경도
    private String rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

}
