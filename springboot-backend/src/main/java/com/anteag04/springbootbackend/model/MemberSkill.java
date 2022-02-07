package com.anteag04.springbootbackend.model;

import com.anteag04.springbootbackend.model.MemberSkillKey;

import javax.persistence.*;

@Entity
@Table(name="memberSkill")
public class MemberSkill {

    @EmbeddedId
    public MemberSkillKey id;
    private String level;

    public MemberSkill(MemberSkillKey id, String level) {
        this.id = id;
        this.level = level;
    }

    public MemberSkillKey getId() {
        return id;
    }

    public void setId(MemberSkillKey id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public MemberSkill() {
    }

}
