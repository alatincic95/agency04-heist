package com.anteag04.springbootbackend.helper;

public class SkillHelperMembers {
    private Long memberId;
    private String name;
    private String level;

    public SkillHelperMembers() {
    }

    public SkillHelperMembers(Long memberId, String name, String level) {
        this.memberId = memberId;
        this.name = name;
        this.level = level;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
