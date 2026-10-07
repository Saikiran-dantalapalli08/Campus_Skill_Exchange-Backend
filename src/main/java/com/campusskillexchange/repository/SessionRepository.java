package com.campusskillexchange.repository;

import com.campusskillexchange.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT s FROM Session s WHERE s.teacher.id = :userId OR s.learner.id = :userId ORDER BY s.date, s.time")
    List<Session> findByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(s) FROM Session s WHERE s.status = 'COMPLETED'")
    long countCompletedSessions();
}
