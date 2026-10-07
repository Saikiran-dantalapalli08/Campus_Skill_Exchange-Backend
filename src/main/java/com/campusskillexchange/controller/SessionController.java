package com.campusskillexchange.controller;

import com.campusskillexchange.dto.SessionDTO;
import com.campusskillexchange.model.Session;
import com.campusskillexchange.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/sessions/{userId}")
    public List<Session> getSessionsForUser(@PathVariable Long userId) {
        return sessionService.getSessionsForUser(userId);
    }

    @PostMapping("/sessions")
    public ResponseEntity<Session> createSession(@RequestBody SessionDTO dto) {
        Session created = sessionService.createSession(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/sessions/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable Long id, @RequestBody SessionDTO dto) {
        return ResponseEntity.ok(sessionService.updateSession(id, dto));
    }
}
