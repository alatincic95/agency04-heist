package com.anteag04.springbootbackend.repository;

import com.anteag04.springbootbackend.model.MemberSkill;
import com.anteag04.springbootbackend.model.MemberSkillKey;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberSkillRepository extends JpaRepositoryImplementation<MemberSkill, MemberSkillKey> {
}
