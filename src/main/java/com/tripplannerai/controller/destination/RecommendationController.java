package com.tripplannerai.controller.destination;

import com.tripplannerai.dto.response.RecommendationResponse;
import com.tripplannerai.service.recommend.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<RecommendationResponse> getRecommendation(@RequestParam String category) {
        RecommendationResponse recommendations = recommendationService.getRecommendation(category);
        return ResponseEntity.ok(recommendations);
    }


}
