package com.anteag04.springbootbackend.helper;

import com.anteag04.springbootbackend.model.SkillsRefactoredHeist;

import java.util.List;

public class HeistWithSkills {
    private Long id;
    private String name;
    private String location;
    private String startTime;
    private  String endTime;
    private List<SkillsRefactoredHeist> skills;
    private String[] members;
    private String status = "PLANNING";
    private  String outcome;



    public HeistWithSkills() {
    }

    public HeistWithSkills(Long id, String name, String location, String startTime, String endTime, List<SkillsRefactoredHeist> skills, String[] members, String status) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.skills = skills;
        this.members = members;
        this.status = status;
    }

    public String[] getMembers() {
        return members;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<SkillsRefactoredHeist> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillsRefactoredHeist> skills) {
        this.skills = skills;
    }
}
