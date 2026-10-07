package com.campusskillexchange.repository;

import com.campusskillexchange.model.LearningRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningRequestRepository extends JpaRepository<LearningRequest, Long> {
    List<LearningRequest> findBySenderId(Long senderId);
    List<LearningRequest> findByReceiverId(Long receiverId);
}
