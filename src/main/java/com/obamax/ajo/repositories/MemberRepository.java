package com.obamax.ajo.repositories;

import com.obamax.ajo.models.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmailAddress(String emailAddress);
    Optional<Member> findByEmailAddress(String emailAddress);

}
