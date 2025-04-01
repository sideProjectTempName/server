package com.tripplannerai.service.recommend;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripplannerai.dto.request.recommend.RecommendRequestDto;
import com.tripplannerai.dto.response.recommend.RecommendationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

@Service
public class RecommendationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);
    private final String FASTAPI_URL = "http://localhost:8000/api/v1/recommendations/";
    public RecommendationResponse getRecommendation(RecommendRequestDto requestDto) throws JsonProcessingException {
        long diffInMilliSeconds = Math.abs(requestDto.getEndDate().getTime()-requestDto.getStartDate().getTime());
        int days = (int) (diffInMilliSeconds / (1000 * 60 * 60 * 24))+1;
        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> jsonRequest = new HashMap<>();
        jsonRequest.put("area_code", requestDto.getAreaCode());
        jsonRequest.put("sigungu_code", requestDto.getSigunguCode());
        jsonRequest.put("category_codes", requestDto.getCategoryCodes());
        jsonRequest.put("days", days);
        String jsonBody = objectMapper.writeValueAsString(jsonRequest);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody,headers);

        // POST 요청 보내기
        ResponseEntity<RecommendationResponse> response = restTemplate.exchange(FASTAPI_URL, HttpMethod.POST, entity, RecommendationResponse.class );

        // 응답 본문 반환
        return response.getBody();
    }
}

