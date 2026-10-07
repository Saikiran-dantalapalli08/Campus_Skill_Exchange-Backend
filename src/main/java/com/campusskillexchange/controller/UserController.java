package com.campusskillexchange.controller;

import com.campusskillexchange.dto.UserProfileDTO;
import com.campusskillexchange.model.User;
import com.campusskillexchange.model.UserSkill;
import com.campusskillexchange.model.UserSkillType;
import com.campusskillexchange.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserProfileDTO dto) {
        return userService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/skills")
    public List<UserSkill> getUserSkills(@PathVariable Long id) {
        return userService.getUserSkills(id);
    }

    @PostMapping("/{id}/skills")
    public UserSkill addUserSkill(@PathVariable Long id, @RequestParam Long skillId, @RequestParam String type) {
        UserSkillType skillType = UserSkillType.valueOf(type.toUpperCase());
        return userService.addUserSkill(id, skillId, skillType);
    }

    @DeleteMapping("/{id}/skills/{skillId}")
    public ResponseEntity<Void> removeUserSkill(@PathVariable Long id, @PathVariable Long skillId) {
        userService.removeUserSkill(id, skillId);
        return ResponseEntity.noContent().build();
    }
}
