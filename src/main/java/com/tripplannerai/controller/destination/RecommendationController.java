package com.tripplannerai.controller.destination;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tripplannerai.dto.request.recommend.RecommendRequestDto;
import com.tripplannerai.dto.response.recommend.RecommendationResponse;
import com.tripplannerai.service.recommend.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<RecommendationResponse> getRecommendation(@ModelAttribute RecommendRequestDto requestDto) throws JsonProcessingException {
        RecommendationResponse recommendations = recommendationService.getRecommendation(requestDto);
        return ResponseEntity.ok(recommendations);
    }


}
