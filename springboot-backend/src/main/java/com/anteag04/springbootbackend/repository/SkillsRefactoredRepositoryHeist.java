package com.anteag04.springbootbackend.repository;


import com.anteag04.springbootbackend.model.Member;
import com.anteag04.springbootbackend.model.Skill;
import com.anteag04.springbootbackend.model.SkillsRefactored;
import com.anteag04.springbootbackend.model.SkillsRefactoredHeist;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRefactoredRepositoryHeist extends JpaRepositoryImplementation<SkillsRefactoredHeist, Long> {
    public List<SkillsRefactoredHeist> findByIDHeist(Long IDHeist);
    public List<SkillsRefactoredHeist> findByskillName(String skillName);
    public List<SkillsRefactoredHeist> deleteByskillName(String skillName);


}