package com.campusskillexchange.service;

import com.campusskillexchange.dto.SessionDTO;
import com.campusskillexchange.model.Session;
import com.campusskillexchange.model.SessionStatus;
import com.campusskillexchange.model.Skill;
import com.campusskillexchange.model.User;
import com.campusskillexchange.repository.SessionRepository;
import com.campusskillexchange.repository.SkillRepository;
import com.campusskillexchange.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public SessionService(SessionRepository sessionRepository,
                          UserRepository userRepository,
                          SkillRepository skillRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    public Session createSession(SessionDTO dto) {
        User teacher = userRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        User learner = userRepository.findById(dto.getLearnerId())
                .orElseThrow(() -> new RuntimeException("Learner not found"));
        Skill skill = skillRepository.findById(dto.getSkillId())
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        Session session = new Session(teacher, learner, skill, dto.getDate(), dto.getTime(), dto.getDuration(), dto.getMeetingLink());
        return sessionRepository.save(session);
    }

    public List<Session> getSessionsForUser(Long userId) {
        return sessionRepository.findByUserId(userId);
    }

    public Session updateSession(Long sessionId, SessionDTO dto) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setDate(dto.getDate());
        session.setTime(dto.getTime());
        session.setDuration(dto.getDuration());
        session.setMeetingLink(dto.getMeetingLink());
        return sessionRepository.save(session);
    }

    public Session completeSession(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setStatus(SessionStatus.COMPLETED);
        return sessionRepository.save(session);
    }
}
