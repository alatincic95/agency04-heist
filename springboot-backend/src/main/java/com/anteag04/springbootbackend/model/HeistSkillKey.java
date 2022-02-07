package com.anteag04.springbootbackend.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class HeistSkillKey implements Serializable {

    public HeistSkillKey() {
    }

    private static final long serialVersionUID = -4400289794364624048L;
    Long heistId;
    Long skillId;

    public Long getHeistId() {
        return heistId;
    }

    public void setHeistId(Long heistId) {
        this.heistId = heistId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }
}
