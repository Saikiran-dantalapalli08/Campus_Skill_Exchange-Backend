package com.campusskillexchange.service;

import com.campusskillexchange.dto.UserProfileDTO;
import com.campusskillexchange.model.Skill;
import com.campusskillexchange.model.User;
import com.campusskillexchange.model.UserSkill;
import com.campusskillexchange.model.UserSkillType;
import com.campusskillexchange.repository.SkillRepository;
import com.campusskillexchange.repository.UserRepository;
import com.campusskillexchange.repository.UserSkillRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final UserSkillRepository userSkillRepository;

    public UserService(UserRepository userRepository,
                      SkillRepository skillRepository,
                      UserSkillRepository userSkillRepository) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.userSkillRepository = userSkillRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long userId, UserProfileDTO dto) {
        User user = getUserById(userId);
        user.setName(dto.getName());
        user.setDepartment(dto.getDepartment());
        user.setYear(dto.getYear());
        user.setBio(dto.getBio());
        user.setProfileImage(dto.getProfileImage());
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<UserSkill> getUserSkills(Long userId) {
        return userSkillRepository.findByUserId(userId);
    }

    public UserSkill addUserSkill(Long userId, Long skillId, UserSkillType type) {
        User user = getUserById(userId);
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        UserSkill userSkill = new UserSkill(user, skill, type);
        return userSkillRepository.save(userSkill);
    }

    public void removeUserSkill(Long userId, Long skillId) {
        userSkillRepository.deleteByUserIdAndSkillId(userId, skillId);
    }

    public List<UserSkill> getTeachSkills(Long userId) {
        return userSkillRepository.findByUserIdAndType(userId, UserSkillType.TEACH);
    }

    public List<UserSkill> getLearnSkills(Long userId) {
        return userSkillRepository.findByUserIdAndType(userId, UserSkillType.LEARN);
    }
}
