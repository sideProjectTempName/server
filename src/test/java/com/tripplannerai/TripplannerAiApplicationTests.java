package com.tripplannerai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

//@SpringBootTest
class TripplannerAiApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void test() throws URISyntaxException {
        String key = "KWUVj66GAr0fQx3Z5iw3q4M3UzVq1RjUQqdOS8ySbqVaKp8hLdpoiqZXGBek77pO00OknYn7rwAxxxYpH6pNJA%3D%3D";
        String format = "https://apis.data.go.kr/B551011/KorService1/" +
                "detailCommon1?ServiceKey=%s&contentTypeId=12&contentId=2605187" +
                "&MobileOS=ETC&MobileApp=AppTest&defaultYN=Y&firstImageYN=Y" +
                "&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&_type=json";
        System.out.println(format.formatted(key));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        URI uri = new URI(format.formatted(key));
        String response = restTemplate.getForObject(uri, String.class);
        System.out.println("Response: " + response);
    }
}
