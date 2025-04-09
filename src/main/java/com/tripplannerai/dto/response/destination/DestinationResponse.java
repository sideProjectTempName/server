package com.tripplannerai.dto.response.destination;

import com.tripplannerai.entity.destination.Destination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DestinationResponse {
    private String contentId;

    public static DestinationResponse of(DestinationQuery destinationQuery) {
        return new DestinationResponse(destinationQuery.getContentId());
    }

    public static DestinationResponse of(Destination destination) {
        return new DestinationResponse(destination.getContentId());
    }
}
