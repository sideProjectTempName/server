package com.tripplannerai.controller.destination;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tripplannerai.annotation.Username;
import com.tripplannerai.dto.request.recommend.RecommendRequestDto;
import com.tripplannerai.dto.request.recommend.SaveRecommendRequest;
import com.tripplannerai.dto.response.plan.DeletePlanResponse;
import com.tripplannerai.dto.response.plan.PlanResponseDto;
import com.tripplannerai.dto.response.recommend.DayScheduleDto;
import com.tripplannerai.dto.response.recommend.RecommendationResponse;
import com.tripplannerai.entity.plan.Plan;
import com.tripplannerai.service.myplan.PlanService;
import com.tripplannerai.service.recommend.AiRecommendationService;
import com.tripplannerai.service.recommend.AiRecommendationServiceImpl;
import com.tripplannerai.util.ConstClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tripplannerai.util.ConstClass.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class AiRecommendationController {
    private final AiRecommendationService recommendationService;
    private final PlanService planService;

    @GetMapping
    public ResponseEntity<RecommendationResponse> getRecommendation(@ModelAttribute RecommendRequestDto requestDto, @Username String email) throws JsonProcessingException {
        RecommendationResponse recommendations = recommendationService.getRecommendation(requestDto);
        return ResponseEntity.ok(recommendations);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveRecommendation(@RequestBody SaveRecommendRequest request, @Username String email) {
        recommendationService.saveRecommendation(request,email);
        return ResponseEntity.ok().build();
    }

}
