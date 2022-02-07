package com.anteag04.springbootbackend.helper;

import com.anteag04.springbootbackend.model.SkillsRefactored;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class MemberWithSkills {


    private long id;


    private String name;


    private String sex;


    private String email;

    private List<SkillsRefactored> skils;


    private String mainSkill;


    private String status;


    public MemberWithSkills(long id, String name, String sex, String email, List<SkillsRefactored> skils, String mainSkill, String status) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.skils = skils;
        this.mainSkill = mainSkill;
        this.status = status;
    }

    public MemberWithSkills() {
    }

    public List<SkillsRefactored> getSkils() {
        return skils;
    }

    public void setSkils(List<SkillsRefactored> skils) {
        this.skils = skils;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getMainSkill() {
        return mainSkill;
    }

    public void setMainSkill(String mainSkill) {
        this.mainSkill = mainSkill;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
