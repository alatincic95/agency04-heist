package com.anteag04.springbootbackend.model;

import com.anteag04.springbootbackend.model.MemberSkillKey;

import javax.persistence.*;

@Entity
@Table(name="heistSkill")
public class HeistSkill {

    @EmbeddedId
    public HeistSkillKey id;
    private String level;
    private String members;

    public HeistSkillKey getId() {
        return id;
    }

    public void setId(HeistSkillKey id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}
