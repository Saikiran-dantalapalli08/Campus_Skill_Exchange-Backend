package com.campusskillexchange.controller;

import com.campusskillexchange.model.LearningRequest;
import com.campusskillexchange.model.Session;
import com.campusskillexchange.model.User;
import com.campusskillexchange.repository.FeedbackRepository;
import com.campusskillexchange.repository.LearningRequestRepository;
import com.campusskillexchange.repository.SessionRepository;
import com.campusskillexchange.repository.SkillRepository;
import com.campusskillexchange.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final LearningRequestRepository learningRequestRepository;
    private final SessionRepository sessionRepository;
    private final FeedbackRepository feedbackRepository;
    private final SkillRepository skillRepository;

    public AdminController(UserRepository userRepository,
                          LearningRequestRepository learningRequestRepository,
                          SessionRepository sessionRepository,
                          FeedbackRepository feedbackRepository,
                          SkillRepository skillRepository) {
        this.userRepository = userRepository;
        this.learningRequestRepository = learningRequestRepository;
        this.sessionRepository = sessionRepository;
        this.feedbackRepository = feedbackRepository;
        this.skillRepository = skillRepository;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", userRepository.findAll().size());
        stats.put("totalSkills", skillRepository.count());
        stats.put("totalLearningRequests", learningRequestRepository.count());
        stats.put("totalSessions", sessionRepository.count());
        stats.put("completedSessions", sessionRepository.countCompletedSessions());
        return stats;
    }
}
