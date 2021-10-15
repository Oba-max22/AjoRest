package com.obamax.ajo.repositories;

import com.obamax.ajo.models.ContributionCycle;
import com.obamax.ajo.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByContributionCycle(ContributionCycle contributionCycle);
}
