package com.obamax.ajo.repositories;

import com.obamax.ajo.models.ContributionCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionCycleRepository extends JpaRepository<ContributionCycle, Long> {
}
