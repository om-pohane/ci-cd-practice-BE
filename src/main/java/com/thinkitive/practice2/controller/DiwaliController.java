package com.thinkitive.practice2.controller;

import com.thinkitive.practice2.dto.CreateWishRequest;
import com.thinkitive.practice2.dto.SubmitScoreRequest;
import com.thinkitive.practice2.entity.DiwaliWish;
import com.thinkitive.practice2.entity.SafaiScore;
import com.thinkitive.practice2.service.DiwaliService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diwali")
@CrossOrigin(origins = "*")
public class DiwaliController {

    private final DiwaliService diwaliService;

    public DiwaliController(DiwaliService diwaliService) {
        this.diwaliService = diwaliService;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> checkHealth() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("festival", "Diwali 2026");
        status.put("message", "Ghar Ki Safai Engine is running! 🧹✨");
        return ResponseEntity.ok(status);
    }

    @PostMapping("/wishes")
    public ResponseEntity<DiwaliWish> createWish(@RequestBody CreateWishRequest req) {
        DiwaliWish created = diwaliService.createWish(req);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/wishes/{code}")
    public ResponseEntity<DiwaliWish> getWish(@PathVariable String code) {
        return diwaliService.getWishByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/scores")
    public ResponseEntity<SafaiScore> submitScore(@RequestBody SubmitScoreRequest req) {
        SafaiScore score = diwaliService.submitScore(req);
        return ResponseEntity.ok(score);
    }

    @GetMapping("/scores/leaderboard")
    public ResponseEntity<List<SafaiScore>> getLeaderboard() {
        return ResponseEntity.ok(diwaliService.getLeaderboard());
    }

    @GetMapping("/scores/wish/{code}")
    public ResponseEntity<List<SafaiScore>> getScoresForWish(@PathVariable String code) {
        return ResponseEntity.ok(diwaliService.getScoresForWish(code));
    }
}
