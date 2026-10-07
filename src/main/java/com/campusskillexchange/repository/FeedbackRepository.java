package com.campusskillexchange.repository;

import com.campusskillexchange.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Optional<Feedback> findBySessionId(Long sessionId);
    List<Feedback> findByReviewerId(Long reviewerId);

    @Query("SELECT f FROM Feedback f JOIN f.session s WHERE s.teacher.id = :teacherId")
    List<Feedback> findByTeacherId(@Param("teacherId") Long teacherId);
}
