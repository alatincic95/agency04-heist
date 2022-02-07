package com.anteag04.springbootbackend.helper;

public class SkillHelperHeist {
    private String name;
    private String level;

    public SkillHelperHeist() {
    }

    public SkillHelperHeist(String name, String level) {
        this.name = name;
        this.level = level;
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
