package com.tripplannerai.service.category;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripplannerai.entity.category.Category;
import com.tripplannerai.mapper.CategoryFactory;
import com.tripplannerai.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplement implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Value("${tourapi.service-key}")
    private String serviceKey;
    @Value("${tourapi.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void saveCategoryFromApi() throws Exception {
        if (categoryRepository.count() > 0) {
            return;
        }
        //대분류 코드 가져오기
        String cat1Url = baseUrl + "/categoryCode1?serviceKey=" + serviceKey +
                "&numOfRows=10000&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";
        JsonNode cat1List = fetchData(cat1Url).get("response").path("body").path("items").path("item");
        for (JsonNode cat1 : cat1List) {
            String cat1Code = cat1.path("code").asText();
            String cat1Name = cat1.path("name").asText();
            Category parent = CategoryFactory.of(cat1Name,cat1Code);
            parent = categoryRepository.save(parent);
            //중분류 가져오기
            String cat2Url = baseUrl + "/categoryCode1?serviceKey=" + serviceKey +
                    "&cat1=" + cat1Code +
                    "&numOfRows=10000&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";
            JsonNode cat2List = fetchData(cat2Url).path("response").path("body").path("items").path("item");
            for (JsonNode cat2 : cat2List) {
                String cat2Code = cat2.path("code").asText();
                String cat2Name = cat2.path("name").asText();
                Category child1 = CategoryFactory.of(cat2Name,cat2Code,parent);
                child1 = categoryRepository.save(child1);

                //소분류 가져오기
                String cat3Url = baseUrl + "/categoryCode1?serviceKey=" + serviceKey +
                        "&cat1=" + cat1Code + "&cat2=" + cat2Code +
                        "&numOfRows=10000&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";
                JsonNode cat3List = fetchData(cat3Url).path("response").path("body").path("items").path("item");
                for (JsonNode cat3 : cat3List) {
                    String cat3Code = cat3.path("code").asText();
                    String cat3Name = cat3.path("name").asText();

                    Category child2 = CategoryFactory.of(cat3Name, cat3Code, child1);
                    categoryRepository.save(child2);
                }
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
