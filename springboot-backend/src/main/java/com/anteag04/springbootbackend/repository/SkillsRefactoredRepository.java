package com.anteag04.springbootbackend.repository;


import com.anteag04.springbootbackend.model.Member;
import com.anteag04.springbootbackend.model.Skill;
import com.anteag04.springbootbackend.model.SkillsRefactored;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRefactoredRepository extends JpaRepositoryImplementation<SkillsRefactored, Long> {
    public List<SkillsRefactored> findByIDMember(Long IDMember);
    public List<SkillsRefactored> findByskillName(String skillName);
    public List<SkillsRefactored> deleteByskillName(String skillName);
    public List<SkillsRefactored> deleteByIDMember(Long IDMember);


}