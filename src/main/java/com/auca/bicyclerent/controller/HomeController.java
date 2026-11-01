package com.auca.bicyclerent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("application", "Bicycle Rent Management System");
        response.put("status", "running");
        response.put("apiBase", "/api");
        return response;
    }
}
