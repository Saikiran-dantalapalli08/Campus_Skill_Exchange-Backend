package com.campusskillexchange.service;

import com.campusskillexchange.dto.LoginRequest;
import com.campusskillexchange.dto.RegisterRequest;
import com.campusskillexchange.model.Skill;
import com.campusskillexchange.model.User;
import com.campusskillexchange.model.UserSkill;
import com.campusskillexchange.model.UserSkillType;
import com.campusskillexchange.repository.SkillRepository;
import com.campusskillexchange.repository.UserRepository;
import com.campusskillexchange.repository.UserSkillRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final UserSkillRepository userSkillRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       SkillRepository skillRepository,
                       UserSkillRepository userSkillRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.userSkillRepository = userSkillRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User register(RegisterRequest request) {
        if (request.getName() == null || request.getName().isBlank()
                || request.getEmail() == null || request.getEmail().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()
                || request.getDepartment() == null || request.getDepartment().isBlank()
                || request.getYear() == null || request.getYear().isBlank()) {
            throw new IllegalArgumentException("Please fill all required fields.");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Password confirmation does not match.");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered.");
        }

        String normalizedRole = request.getRole() == null || request.getRole().isBlank()
                ? "ROLE_STUDENT"
                : request.getRole().toUpperCase();

        if (!normalizedRole.startsWith("ROLE_")) {
            normalizedRole = "ROLE_" + normalizedRole;
        }

        User user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getDepartment(),
                request.getYear(),
                request.getBio(),
                normalizedRole,
                "https://ui-avatars.com/api/?name=" + request.getName().replace(" ", "+")
        );

        User savedUser = userRepository.save(user);

        assignSkills(savedUser, request.getTeachSkillIds(), UserSkillType.TEACH);
        assignSkills(savedUser, request.getLearnSkillIds(), UserSkillType.LEARN);

        return savedUser;
    }

    public User login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    }

    private void assignSkills(User user, Set<Long> skillIds, UserSkillType type) {
        if (skillIds == null || skillIds.isEmpty()) {
            return;
        }

        for (Long skillId : skillIds) {
            Skill skill = skillRepository.findById(skillId)
                    .orElseThrow(() -> new IllegalArgumentException("Skill not found with id: " + skillId));

            UserSkill userSkill = new UserSkill(user, skill, type);
            userSkillRepository.save(userSkill);
        }
    }
}
