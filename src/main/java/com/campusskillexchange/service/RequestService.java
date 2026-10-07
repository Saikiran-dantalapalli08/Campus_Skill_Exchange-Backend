package com.campusskillexchange.service;

import com.campusskillexchange.dto.LearningRequestDTO;
import com.campusskillexchange.model.LearningRequest;
import com.campusskillexchange.model.RequestStatus;
import com.campusskillexchange.model.Session;
import com.campusskillexchange.model.Skill;
import com.campusskillexchange.model.User;
import com.campusskillexchange.repository.LearningRequestRepository;
import com.campusskillexchange.repository.SessionRepository;
import com.campusskillexchange.repository.SkillRepository;
import com.campusskillexchange.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class RequestService {

    private final LearningRequestRepository learningRequestRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final SessionRepository sessionRepository;

    public RequestService(LearningRequestRepository learningRequestRepository,
                          UserRepository userRepository,
                          SkillRepository skillRepository,
                          SessionRepository sessionRepository) {
        this.learningRequestRepository = learningRequestRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.sessionRepository = sessionRepository;
    }

    public LearningRequest createRequest(LearningRequestDTO dto) {
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        Skill skill = skillRepository.findById(dto.getSkillId())
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        LearningRequest request = new LearningRequest(sender, receiver, skill, dto.getMessage());
        return learningRequestRepository.save(request);
    }

    public List<LearningRequest> getReceivedRequests(Long userId) {
        return learningRequestRepository.findByReceiverId(userId);
    }

    public List<LearningRequest> getSentRequests(Long userId) {
        return learningRequestRepository.findBySenderId(userId);
    }

    public LearningRequest acceptRequest(Long requestId) {
        LearningRequest request = learningRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (request.getStatus() == RequestStatus.ACCEPTED) {
            return request;
        }

        request.setStatus(RequestStatus.ACCEPTED);
        learningRequestRepository.save(request);

        Session session = new Session(
                request.getReceiver(),
                request.getSender(),
                request.getSkill(),
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0),
                "60 minutes",
                "https://meet.google.com/lookup-session"
        );
        sessionRepository.save(session);

        return request;
    }

    public LearningRequest rejectRequest(Long requestId) {
        LearningRequest request = learningRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(RequestStatus.REJECTED);
        return learningRequestRepository.save(request);
    }
}
