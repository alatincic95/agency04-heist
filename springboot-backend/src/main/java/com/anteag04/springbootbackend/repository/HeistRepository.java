package com.anteag04.springbootbackend.repository;

import com.anteag04.springbootbackend.model.Heist;
import com.anteag04.springbootbackend.model.Member;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface HeistRepository extends JpaRepositoryImplementation<Heist, Long> {
    Heist findByName(String name);
}
