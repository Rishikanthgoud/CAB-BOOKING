package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DistanceService {

    @Value("${ors.api.key}")
    private String apiKey;

    public double calculateDistance(double pickupLat, double pickupLon,
                                    double dropLat, double dropLon) {

        String url = "https://api.openrouteservice.org/v2/directions/driving-car";

        RestTemplate restTemplate = new RestTemplate();

        // 🔹 Step 1: Headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 🔹 Step 2: Body
        Map<String, Object> body = new HashMap<>();
        List<List<Double>> coordinates = new ArrayList<>();

        // IMPORTANT: [longitude, latitude]
        coordinates.add(Arrays.asList(pickupLon, pickupLat));
        coordinates.add(Arrays.asList(dropLon, dropLat));

        body.put("coordinates", coordinates);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        // 🔹 Step 3: API Call
        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        // 🔹 Step 4: Extract distance
        Map responseBody = response.getBody();

        List routes = (List) responseBody.get("routes");
        Map firstRoute = (Map) routes.get(0);

        Map summary = (Map) firstRoute.get("summary");

        double distanceMeters = (double) summary.get("distance");

        // 🔹 Step 5: Convert to KM
        return distanceMeters / 1000.0;
    }
}