package com.tripplannerai.entity.coursespot;

import com.tripplannerai.entity.course.Course;
import com.tripplannerai.entity.destination.Destination;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "course_spot")
@NoArgsConstructor
@AllArgsConstructor
public class CourseSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseSpotId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Destination destination;
    @Lob
    private String description;
    private Integer day;
    private String timeSlot;

}
