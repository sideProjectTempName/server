package com.tripplannerai.service.destination;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripplannerai.entity.address.Address;
import com.tripplannerai.entity.category.Category;
import com.tripplannerai.entity.destination.Destination;
import com.tripplannerai.mapper.destination.DestinationFactory;
import com.tripplannerai.repository.address.AddressRepository;
import com.tripplannerai.repository.category.CategoryRepository;
import com.tripplannerai.repository.destination.DestinationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class DestinationServiceImplement implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final CategoryRepository categoryRepository;
    private final AddressRepository addressRepository;
    @Value("${tourapi.service-key}")
    private String serviceKey;
    @Value("${tourapi.base-url}")
    private String baseUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void saveDestinationFromApi() throws Exception {
        if (destinationRepository.count() > 0) {
            return;
        }
        int pageNo = 1;
        int numOfRows = 1000;
        boolean hasMore = true;
        while (hasMore) {
            String destinationUrl = baseUrl + "/areaBasedList1?serviceKey=" + serviceKey +
                    "&numOfRows="+numOfRows+"&pageNo="+pageNo+"&MobileOS=ETC&MobileApp=AppTest&_type=json";
            JsonNode responseBody = fetchData(destinationUrl).get("response").path("body");
            JsonNode destinationList = responseBody.path("items").path("item");
            for (JsonNode destinationNode : destinationList) {
                Destination destination = DestinationFactory.fromApiResponse(destinationNode);
                String cat1 = destinationNode.path("cat1").asText();
                if(cat1.equals("25")) continue; // 여행코스는 저장하지 않음
                String cat2 = destinationNode.path("cat2").asText();
                String cat3 = destinationNode.path("cat3").asText();
                Category category = categoryRepository.findCategoryByCodes(cat1,cat2,cat3).orElse(null);
                String areaCode = destinationNode.path("areacode").asText();
                String sigunguCode = destinationNode.path("sigungucode").asText();
                Address address = addressRepository.findAddressByCodes(areaCode,sigunguCode).orElse(null);

                destination.setCategory(category);
                destination.setAddress(address);
                destinationRepository.save(destination);
            }

            int totalCount = Integer.parseInt(responseBody.path("totalCount").asText());
            int totalPages = (int) Math.ceil((double) totalCount/numOfRows);
            if (pageNo < totalPages) {
                pageNo++;
            } else {
                hasMore = false;
            }
        }


    }

    private JsonNode fetchData(String url) throws Exception{
        try {
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            URI uri = new URI(url);
            String response = restTemplate.getForObject(uri, String.class);

            return objectMapper.readTree(response);
        } catch (Exception e) {
            throw new RuntimeException("API 호출 실패: " + url,e);
        }
    }
}
