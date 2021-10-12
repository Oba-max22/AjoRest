package com.obamax.ajo.services.implementation;

import com.obamax.ajo.dto.ContributionCycleDTO;
import com.obamax.ajo.exceptions.BadRequestException;
import com.obamax.ajo.models.ContributionCycle;
import com.obamax.ajo.repositories.ContributionCycleRepository;
import com.obamax.ajo.services.ContributionCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public ContributionCycle editCycle(ContributionCycleDTO contributionCycleRequest, Long cycleId) {
        Optional<ContributionCycle> contributionCycle = contributionCycleRepository.findById(cycleId);

        if (contributionCycle.isPresent()) {
            contributionCycle.get().setName(contributionCycleRequest.getName());
            contributionCycle.get().setStartDate(contributionCycleRequest.getStartDate());
            contributionCycle.get().setEndDate(contributionCycleRequest.getEndDate());
            contributionCycle.get().setPaymentStartDate(contributionCycleRequest.getPaymentStartDate());
            contributionCycle.get().setPaymentEndDate(contributionCycleRequest.getPaymentEndDate());
            contributionCycle.get().setMonthlyAmount(contributionCycleRequest.getMonthlyAmount());

            contributionCycleRepository.save(contributionCycle.get());
            return contributionCycle.get();
        } else {
            throw new BadRequestException("Something went wrong! ContributionCycle not found.");
        }
    }

}
