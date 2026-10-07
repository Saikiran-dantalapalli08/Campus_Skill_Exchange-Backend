package com.campusskillexchange.service;

import com.campusskillexchange.model.Skill;
import com.campusskillexchange.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill createSkill(Skill skill) {
        if (skill.getName() == null || skill.getName().isBlank()) {
            throw new IllegalArgumentException("Skill name is required.");
        }
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long skillId) {
        skillRepository.deleteById(skillId);
    }
}
