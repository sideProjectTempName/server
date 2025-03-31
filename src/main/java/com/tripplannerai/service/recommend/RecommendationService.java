package com.tripplannerai.service.recommend;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripplannerai.dto.response.RecommendationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;

@Service
public class RecommendationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);
    private final String FASTAPI_URL = "http://127.0.0.1:8000/recommend/?category=";
    public RecommendationResponse getRecommendation(String category) {
        String url = FASTAPI_URL + category;

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // GET 요청 보내기
        ResponseEntity<RecommendationResponse > response = restTemplate.exchange(url, HttpMethod.GET, entity, RecommendationResponse.class );

        // 응답 본문 반환
        return response.getBody();
    }
}

