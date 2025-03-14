package com.tripplannerai.entity.destination;

import com.tripplannerai.entity.address.Address;
import com.tripplannerai.entity.category.Category;
import com.tripplannerai.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "destination")
public class Destination {
    @Id
    @Column(name = "destination_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long destinationId;
    private String name;
    @Lob
    private String description;
    private String latitude;
    private String longitude;
    private String rating;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address+id")
    private Address address;

}
