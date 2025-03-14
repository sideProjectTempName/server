package com.tripplannerai.entity.itinerary;

import com.tripplannerai.entity.plan.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itinerary_id")
    private Long itineraryId;
    private int day;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_Id")
    private Plan plan;
}
