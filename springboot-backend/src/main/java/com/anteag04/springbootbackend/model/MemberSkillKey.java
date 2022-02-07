package com.anteag04.springbootbackend.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MemberSkillKey implements Serializable {

    public MemberSkillKey() {
    }

    private static final long serialVersionUID = -4400289794364624048L;
    Long memberId;
    Long skillId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }
}
