package com.example.myapp.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.JsonObject;

@Controller
public class WelcomeController {
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @PostMapping("/storeInput")
    public String storeInput(@RequestParam("textInput") String userInput, Model model) {
        
        model.addAttribute("storedInput", userInput);

        return "result";
    }
    
    @GetMapping("/makeAuthenticatedApiCall")
    public String makeAuthenticatedApiCall(
            @RequestParam("param1") String param1,
            Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://api.weatherapi.com/v1/current.json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("key", "e2119887527643758b541406231409");

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        String urlWithParams = apiUrl + "?q=" + param1;

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                urlWithParams,
                org.springframework.http.HttpMethod.GET,
                requestEntity,
                String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                String apiResponse = response.getBody();
                
                // Parse JSON data
                model.addAttribute("apiResponse", apiResponse);

            } else {
                model.addAttribute("apiResponse", "API Call Failed");
            }
        } catch (Exception e) {
            model.addAttribute("apiResponse", "API Call Error");
        }

        // Return the view name corresponding to the HTML template
        return "result";
    }

    
}