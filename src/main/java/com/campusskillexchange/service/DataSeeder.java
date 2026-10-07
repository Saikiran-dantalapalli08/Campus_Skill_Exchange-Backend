package com.campusskillexchange.service;

import com.campusskillexchange.model.Skill;
import com.campusskillexchange.model.User;
import com.campusskillexchange.model.UserSkill;
import com.campusskillexchange.model.UserSkillType;
import com.campusskillexchange.repository.SkillRepository;
import com.campusskillexchange.repository.UserRepository;
import com.campusskillexchange.repository.UserSkillRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final UserSkillRepository userSkillRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      SkillRepository skillRepository,
                      UserSkillRepository userSkillRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.userSkillRepository = userSkillRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (skillRepository.count() == 0) {
            List<Skill> skillList = List.of(
                    new Skill("Java", "Programming", "Intermediate"),
                    new Skill("C++", "Programming", "Intermediate"),
                    new Skill("Python", "Programming", "Beginner"),
                    new Skill("React", "Web Development", "Advanced"),
                    new Skill("JavaScript", "Web Development", "Intermediate"),
                    new Skill("Machine Learning", "Data Science", "Advanced"),
                    new Skill("Data Science", "Data Science", "Intermediate"),
                    new Skill("SQL", "Data Science", "Intermediate"),
                    new Skill("Graphic Design", "Design", "Intermediate"),
                    new Skill("UI/UX Design", "Design", "Advanced"),
                    new Skill("Public Speaking", "Communication", "Advanced"),
                    new Skill("Digital Marketing", "Marketing", "Beginner"),
                    new Skill("Video Editing", "Marketing", "Intermediate"),
                    new Skill("Communication", "Communication", "Intermediate")
            );
            skillRepository.saveAll(skillList);
        }

        if (userRepository.count() == 0) {
            User admin = new User(
                    "Admin User",
                    "admin@campus.com",
                    passwordEncoder.encode("admin123"),
                    "Computer Science",
                    "4",
                    "Platform administrator",
                    "ROLE_ADMIN",
                    "https://ui-avatars.com/api/?name=Admin+User"
            );
            userRepository.save(admin);

            List<User> students = new ArrayList<>();
            students.add(new User("Rahul Kumar", "rahul@campus.com", passwordEncoder.encode("student123"), "Computer Science", "2", "Love solving coding problems and mentor students.", "ROLE_STUDENT", "https://ui-avatars.com/api/?name=Rahul+Kumar"));
            students.add(new User("Ananya Sharma", "ananya@campus.com", passwordEncoder.encode("student123"), "Data Science", "3", "Passionate about data and research.", "ROLE_STUDENT", "https://ui-avatars.com/api/?name=Ananya+Sharma"));
            students.add(new User("Sai Kiran", "sai@campus.com", passwordEncoder.encode("student123"), "Information Technology", "3", "Interested in web development and learning new frameworks.", "ROLE_STUDENT", "https://ui-avatars.com/api/?name=Sai+Kiran"));
            students.add(new User("Priya Nair", "priya@campus.com", passwordEncoder.encode("student123"), "ECE", "2", "Enjoys design, UX, and building user-friendly apps.", "ROLE_STUDENT", "https://ui-avatars.com/api/?name=Priya+Nair"));
            students.add(new User("Vikas Sharma", "vikas@campus.com", passwordEncoder.encode("student123"), "Mechanical", "4", "Interested in communication and technical presentation.", "ROLE_STUDENT", "https://ui-avatars.com/api/?name=Vikas+Sharma"));
            students.add(new User("Meera Iyer", "meera@campus.com", passwordEncoder.encode("student123"), "EEE", "1", "Curious learner who enjoys backend concepts.", "ROLE_STUDENT", "https://ui-avatars.com/api/?name=Meera+Iyer"));
            students.add(new User("Karthik Rao", "karthik@campus.com", passwordEncoder.encode("student123"), "Computer Science", "3", "Enjoys Java, DSA, and mentoring juniors.", "ROLE_STUDENT", "https://ui-avatars.com/api/?name=Karthik+Rao"));
            students.add(new User("Neha Singh", "neha@campus.com", passwordEncoder.encode("student123"), "Data Science", "2", "Loves UI/UX and design thinking.", "ROLE_STUDENT", "https://ui-avatars.com/api/?name=Neha+Singh"));
            students.add(new User("Arjun Patel", "arjun@campus.com", passwordEncoder.encode("student123"), "IT", "4", "Enjoys marketing and digital content strategy.", "ROLE_STUDENT", "https://ui-avatars.com/api/?name=Arjun+Patel"));

            List<User> savedStudents = userRepository.saveAll(students);
            Map<String, Skill> skillMap = new HashMap<>();
            for (Skill skill : skillRepository.findAll()) {
                skillMap.put(skill.getName(), skill);
            }

            List<UserSkill> userSkills = new ArrayList<>();
            addUserSkill(userSkills, savedStudents.get(0), skillMap, "Java", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(0), skillMap, "Python", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(0), skillMap, "React", UserSkillType.LEARN);
            addUserSkill(userSkills, savedStudents.get(0), skillMap, "UI/UX Design", UserSkillType.LEARN);

            addUserSkill(userSkills, savedStudents.get(1), skillMap, "Python", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(1), skillMap, "Machine Learning", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(1), skillMap, "Java", UserSkillType.LEARN);

            addUserSkill(userSkills, savedStudents.get(2), skillMap, "React", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(2), skillMap, "JavaScript", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(2), skillMap, "Java", UserSkillType.LEARN);

            addUserSkill(userSkills, savedStudents.get(3), skillMap, "UI/UX Design", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(3), skillMap, "Graphic Design", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(3), skillMap, "React", UserSkillType.LEARN);

            addUserSkill(userSkills, savedStudents.get(4), skillMap, "Public Speaking", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(4), skillMap, "Communication", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(4), skillMap, "Digital Marketing", UserSkillType.LEARN);

            addUserSkill(userSkills, savedStudents.get(5), skillMap, "Java", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(5), skillMap, "SQL", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(5), skillMap, "React", UserSkillType.LEARN);

            addUserSkill(userSkills, savedStudents.get(6), skillMap, "Java", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(6), skillMap, "C++", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(6), skillMap, "Machine Learning", UserSkillType.LEARN);

            addUserSkill(userSkills, savedStudents.get(7), skillMap, "UI/UX Design", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(7), skillMap, "Graphic Design", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(7), skillMap, "Java", UserSkillType.LEARN);

            addUserSkill(userSkills, savedStudents.get(8), skillMap, "Digital Marketing", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(8), skillMap, "Video Editing", UserSkillType.TEACH);
            addUserSkill(userSkills, savedStudents.get(8), skillMap, "Public Speaking", UserSkillType.LEARN);

            if (!userSkills.isEmpty()) {
                userSkillRepository.saveAll(userSkills);
            }
        }
    }

    private void addUserSkill(List<UserSkill> userSkills, User user, Map<String, Skill> skillMap, String skillName, UserSkillType type) {
        Skill skill = skillMap.get(skillName);
        if (skill != null) {
            userSkills.add(new UserSkill(user, skill, type));
        }
    }
}
