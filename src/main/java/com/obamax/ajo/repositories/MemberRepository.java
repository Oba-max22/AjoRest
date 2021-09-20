package com.obamax.ajo.repositories;

import com.obamax.ajo.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmailAddress(String emailAddress);
}
