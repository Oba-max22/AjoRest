package com.obamax.ajo.repositories;

import com.obamax.ajo.models.MemberContributionCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberContributionCycleRepository extends JpaRepository<MemberContributionCycle, Long> {
}
