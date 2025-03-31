package com.tripplannerai.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecommendationResponse {
    List<item> recomended_destinations;
    @Data
    @NoArgsConstructor
    public static class item {
        String destination_id;
        String name;
    }
}
