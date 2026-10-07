package com.campusskillexchange.service;

import com.campusskillexchange.model.Feedback;
import com.campusskillexchange.model.User;
import com.campusskillexchange.model.UserSkillType;
import com.campusskillexchange.repository.FeedbackRepository;
import com.campusskillexchange.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MatchService {

    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;

    public MatchService(UserRepository userRepository, FeedbackRepository feedbackRepository) {
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public List<Map<String, Object>> getMatchesForUser(Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<String> desiredSkills = currentUser.getUserSkills().stream()
                .filter(userSkill -> userSkill.getType() == UserSkillType.LEARN)
                .map(userSkill -> userSkill.getSkill().getName())
                .collect(java.util.stream.Collectors.toSet());

        if (desiredSkills.isEmpty()) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> matches = new ArrayList<>();

        for (User candidate : userRepository.findAll()) {
            if (candidate.getId().equals(userId)) {
                continue;
            }

            Set<String> teachSkills = candidate.getUserSkills().stream()
                    .filter(userSkill -> userSkill.getType() == UserSkillType.TEACH)
                    .map(userSkill -> userSkill.getSkill().getName())
                    .collect(java.util.stream.Collectors.toSet());

            Set<String> matchingSkills = new HashSet<>(desiredSkills);
            matchingSkills.retainAll(teachSkills);

            if (matchingSkills.isEmpty()) {
                continue;
            }

            int matchPercentage = (int) Math.round((matchingSkills.size() * 100.0) / desiredSkills.size());
            double rating = getAverageRating(candidate.getId());

            Map<String, Object> match = new java.util.LinkedHashMap<>();
            match.put("user", candidate);
            match.put("matchingSkills", new ArrayList<>(matchingSkills));
            match.put("matchPercentage", matchPercentage);
            match.put("rating", rating);
            matches.add(match);
        }

        matches.sort((a, b) -> Integer.compare(
                (Integer) b.get("matchPercentage"),
                (Integer) a.get("matchPercentage")
        ));

        return matches;
    }

    private double getAverageRating(Long teacherId) {
        List<Feedback> feedbacks = feedbackRepository.findByTeacherId(teacherId);
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
