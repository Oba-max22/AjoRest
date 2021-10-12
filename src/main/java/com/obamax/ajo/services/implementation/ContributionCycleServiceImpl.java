package com.obamax.ajo.services.implementation;

import com.obamax.ajo.dto.ContributionCycleDTO;
import com.obamax.ajo.exceptions.BadRequestException;
import com.obamax.ajo.models.ContributionCycle;
import com.obamax.ajo.repositories.ContributionCycleRepository;
import com.obamax.ajo.services.ContributionCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContributionCycleServiceImpl implements ContributionCycleService {

    @Autowired
    private ContributionCycleRepository contributionCycleRepository;

    @Override
    public ContributionCycle createCycle(ContributionCycleDTO contributionCycleRequest) {
        ContributionCycle contributionCycle = new ContributionCycle();

        if (contributionCycleRequest != null) {
            contributionCycle.setName(contributionCycleRequest.getName());
            contributionCycle.setStartDate(contributionCycleRequest.getStartDate());
            contributionCycle.setEndDate(contributionCycleRequest.getEndDate());
            contributionCycle.setPaymentStartDate(contributionCycleRequest.getPaymentStartDate());
            contributionCycle.setPaymentEndDate(contributionCycleRequest.getPaymentEndDate());
            contributionCycle.setMonthlyAmount(contributionCycleRequest.getMonthlyAmount());

            contributionCycleRepository.save(contributionCycle);
            return contributionCycle;
        } else {
            throw new BadRequestException("Something went wrong! ContributionCycleRequest is null.");
        }

    }
}
