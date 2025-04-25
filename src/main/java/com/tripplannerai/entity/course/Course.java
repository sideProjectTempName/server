package com.tripplannerai.entity.course;

import com.tripplannerai.entity.image.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "course")
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String title;
    private String region;
    private String duration;
    @Lob
    private String description;
    private String transportation;
    private Integer budget;
    private Float rating;
    private Long likeCount;
    private LocalDateTime createdAt;
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image thumbnailImage;

}
