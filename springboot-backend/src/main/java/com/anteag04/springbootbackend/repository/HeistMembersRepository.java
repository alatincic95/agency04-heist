package com.anteag04.springbootbackend.repository;

import com.anteag04.springbootbackend.model.Heist;
import com.anteag04.springbootbackend.model.HeistMembers;
import com.anteag04.springbootbackend.model.Member;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface HeistMembersRepository extends JpaRepositoryImplementation<HeistMembers, Long> {
    ArrayList<HeistMembers> findByheistId(Long heistId);
}
