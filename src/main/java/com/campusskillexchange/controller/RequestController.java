package com.campusskillexchange.controller;

import com.campusskillexchange.dto.LearningRequestDTO;
import com.campusskillexchange.model.LearningRequest;
import com.campusskillexchange.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/requests/received/{userId}")
    public List<LearningRequest> getReceivedRequests(@PathVariable Long userId) {
        return requestService.getReceivedRequests(userId);
    }

    @GetMapping("/requests/sent/{userId}")
    public List<LearningRequest> getSentRequests(@PathVariable Long userId) {
        return requestService.getSentRequests(userId);
    }

    @PostMapping("/requests")
    public ResponseEntity<LearningRequest> createRequest(@RequestBody LearningRequestDTO dto) {
        LearningRequest created = requestService.createRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/requests/{id}/accept")
    public ResponseEntity<LearningRequest> acceptRequest(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.acceptRequest(id));
    }

    @PutMapping("/requests/{id}/reject")
    public ResponseEntity<LearningRequest> rejectRequest(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.rejectRequest(id));
    }
}
