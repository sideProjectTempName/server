package com.tripplannerai.dto.response.recommend;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResponse {
    private Map<String,DaySchedule> schedule;
    private String message;
}
@Data
class DaySchedule {
    private List<Spot> spots;
    private Accommodation accommodation;
}
@Data
class Spot {
    @JsonProperty("destination_id")
    private String destinationId;
    private String name;
    private String addr1;
    private String addr2;
    private Double latitude;
    private Double longitude;
    @JsonProperty("content_id")
    private String contentId;
    @JsonProperty("category_code")
    private String categoryCode;
    @JsonProperty("category_name")
    private String categoryName;
    private String type;

}
@Data
class Accommodation {
    @JsonProperty("destination_id")
    private String destinationId;
    private String name;
    private String addr1;
    private String addr2;
    private Double latitude;
    private Double longitude;
    @JsonProperty("content_id")
    private String contentId;
}