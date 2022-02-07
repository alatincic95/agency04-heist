package com.anteag04.springbootbackend.model;

import javax.persistence.*;

@Entity
@Table(name = "skills_refactored_heist")
public class SkillsRefactoredHeist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "idheist")
    private Long IDHeist;
    private String skillName;
    private String skillLevel;
    private String members;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIDHeist() {
        return IDHeist;
    }

    public void setIDHeist(Long IDHeist) {
        this.IDHeist = IDHeist;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}