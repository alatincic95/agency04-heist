package com.anteag04.springbootbackend.repository;


import com.anteag04.springbootbackend.model.Member;
import com.anteag04.springbootbackend.model.Skill;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepositoryHeist extends JpaRepositoryImplementation<Skill, Long> {
    Skill findByName(String name);
}