package com.campusskillexchange.service;

import com.campusskillexchange.dto.FeedbackDTO;
import com.campusskillexchange.model.Feedback;
import com.campusskillexchange.model.Session;
import com.campusskillexchange.model.User;
import com.campusskillexchange.repository.FeedbackRepository;
import com.campusskillexchange.repository.SessionRepository;
import com.campusskillexchange.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public FeedbackService(FeedbackRepository feedbackRepository,
                           SessionRepository sessionRepository,
                           UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public Feedback createFeedback(FeedbackDTO dto) {
        Session session = sessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session not found"));
        User reviewer = userRepository.findById(dto.getReviewerId())
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));

        if (dto.getRating() < 1 || dto.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        Feedback feedback = new Feedback(session, reviewer, dto.getRating(), dto.getComment());
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbackForUser(Long userId) {
        return feedbackRepository.findByTeacherId(userId);
    }

    public double getAverageRating(Long teacherId) {
        List<Feedback> feedbacks = getFeedbackForUser(teacherId);
        if (feedbacks.isEmpty()) {
            return 0.0;
        }

        double total = 0;
        for (Feedback feedback : feedbacks) {
            total += feedback.getRating();
        }
        return total / feedbacks.size();
    }
}
