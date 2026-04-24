package com.nirmala.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthController {

    @GetMapping("/health")
    public Map<String,Object> healthCheck(){
        Map<String, Object> map = new HashMap<>();
        map.put("status", "UP");
        map.put("message", "Teng-go! Backend is running!");
        map.put("timestamp", System.currentTimeMillis());
        return map;
    }

    @GetMapping("/info")
    public Map<String,Object> appInfo(){
        Map<String,Object> utama = new HashMap<>();
        utama.put("App Name","Teng-go! Backend");
        utama.put("Version", "Beta 1.0");
        utama.put("Description", "A simple backend for Teng-go!");

        Map<String,String> endpoints = new HashMap<>();

        endpoints.put("players", "/player");
        endpoints.put("scores", "/scores");
        endpoints.put("leaderboard", "/leaderboard");
        endpoints.put("health", "/health");

        utama.put("endpoints", endpoints);

        return utama;
    }
}
