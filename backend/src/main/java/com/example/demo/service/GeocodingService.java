package com.example.demo.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GeocodingService {

    public double[] getCoordinates(String location) {

        String url = "https://nominatim.openstreetmap.org/search?q="
                + location + "&format=json&limit=1";

        RestTemplate restTemplate = new RestTemplate();

        // ✅ Add headers (IMPORTANT)
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "cab-booking-app"); // REQUIRED

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                List.class
        );

        List body = response.getBody();

        if (body == null || body.isEmpty()) {
            throw new RuntimeException("Location not found");
        }

        Map result = (Map) body.get(0);

        double lat = Double.parseDouble((String) result.get("lat"));
        double lon = Double.parseDouble((String) result.get("lon"));

        return new double[]{lat, lon};
    }
}