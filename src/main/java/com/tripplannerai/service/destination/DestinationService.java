package com.tripplannerai.service.destination;

import com.tripplannerai.dto.response.destination.DestinationResponse;
import com.tripplannerai.dto.response.destination.DestinationsResponse;

public interface DestinationService {
    void saveDestinationFromApi() throws Exception;

    DestinationResponse fetchDestination(Long destinationId,String email);

    DestinationsResponse fetchDestinations(Integer page, Integer size,String name,String email);

    DestinationsResponse fetchDestinationByCategory(Integer page, Integer size,String category);

    DestinationsResponse fetchDestinationsByTotal();

    DestinationsResponse fetchMyDestinations(Integer page, Integer size, String email);
}
