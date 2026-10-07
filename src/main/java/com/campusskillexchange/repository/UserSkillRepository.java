package com.campusskillexchange.repository;

import com.campusskillexchange.model.UserSkill;
import com.campusskillexchange.model.UserSkillType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findByUserId(Long userId);
    List<UserSkill> findByUserIdAndType(Long userId, UserSkillType type);
    void deleteByUserIdAndSkillId(Long userId, Long skillId);
}
