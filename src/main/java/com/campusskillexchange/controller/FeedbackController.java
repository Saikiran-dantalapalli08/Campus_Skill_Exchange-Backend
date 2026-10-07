package com.campusskillexchange.controller;

import com.campusskillexchange.dto.FeedbackDTO;
import com.campusskillexchange.model.Feedback;
import com.campusskillexchange.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Feedback> createFeedback(@RequestBody FeedbackDTO dto) {
        Feedback created = feedbackService.createFeedback(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/feedback/user/{userId}")
    public List<Feedback> getFeedbackForUser(@PathVariable Long userId) {
        return feedbackService.getFeedbackForUser(userId);
    }
}
