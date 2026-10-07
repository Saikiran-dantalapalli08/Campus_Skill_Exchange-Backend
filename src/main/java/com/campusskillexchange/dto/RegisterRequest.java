package com.campusskillexchange.dto;

import java.util.Set;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String department;
    private String year;
    private String bio;
    private String role = "ROLE_STUDENT";
    private Set<Long> teachSkillIds;
    private Set<Long> learnSkillIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Long> getTeachSkillIds() {
        return teachSkillIds;
    }

    public void setTeachSkillIds(Set<Long> teachSkillIds) {
        this.teachSkillIds = teachSkillIds;
    }

    public Set<Long> getLearnSkillIds() {
        return learnSkillIds;
    }

    public void setLearnSkillIds(Set<Long> learnSkillIds) {
        this.learnSkillIds = learnSkillIds;
    }
}
