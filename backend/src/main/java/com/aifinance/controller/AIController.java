package com.aifinance.controller;

import com.aifinance.service.AIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: zpj
 * 2024/10/23
 */

@RestController
@RequestMapping("/api/chat")
public class AIController {
    private final AIService AIService;

    public AIController(AIService AIService) {
        this.AIService = AIService;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askAI(@RequestBody Map<String, String> payload) {
        String prompt = payload.get("prompt");
        String response = AIService.getResponse(prompt);
        return ResponseEntity.ok(response);sss
    }
}

