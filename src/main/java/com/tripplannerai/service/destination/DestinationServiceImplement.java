package com.tripplannerai.service.destination;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripplannerai.repository.destination.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DestinationServiceImplement implements DestinationService {

    private final DestinationRepository destinationRepository;

    @Value("${tourapi.service-key}")
    private String serviceKey;



    @Override
    public void fetchAndSaveDestinations(String areaCode) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        int pageNo = 1;
        int numOfRows = 1000;
        boolean hasMoreData = true;

    }
}
