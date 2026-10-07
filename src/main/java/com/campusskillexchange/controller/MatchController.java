package com.campusskillexchange.controller;

import com.campusskillexchange.service.MatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/matches/{userId}")
    public List<Map<String, Object>> getMatches(@PathVariable Long userId) {
        return matchService.getMatchesForUser(userId);
    }
}
