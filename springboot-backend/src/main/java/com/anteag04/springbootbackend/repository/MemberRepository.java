package com.anteag04.springbootbackend.repository;

import com.anteag04.springbootbackend.model.Member;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepositoryImplementation<Member, Long> {

    Member findByEmail(String email);
    Member findByName(String name);

}
