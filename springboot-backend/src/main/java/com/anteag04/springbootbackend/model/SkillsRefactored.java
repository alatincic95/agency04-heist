package com.anteag04.springbootbackend.model;

import javax.persistence.*;

@Entity
@Table(name = "skills_refactored")
public class SkillsRefactored {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private Long IDMember;
    private String skillName;
    private String skillLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SkillsRefactored() {
    }

    public SkillsRefactored(Long IDMember, String skillName, String skillLevel) {
        this.IDMember = IDMember;
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }

    public Long getIDMember() {
        return IDMember;
    }

    public void setIDMember(Long IDMember) {
        this.IDMember = IDMember;
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
}
