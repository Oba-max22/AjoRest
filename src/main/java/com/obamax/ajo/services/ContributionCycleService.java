package com.obamax.ajo.services;

import com.obamax.ajo.dto.ContributionCycleDTO;
import com.obamax.ajo.models.ContributionCycle;

public interface ContributionCycleService {

    ContributionCycle createCycle(ContributionCycleDTO contributionCycleRequest);

    ContributionCycle editCycle(ContributionCycleDTO contributionCycleRequest, Long cycleId);
}
