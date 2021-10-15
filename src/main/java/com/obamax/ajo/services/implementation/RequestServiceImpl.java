package com.obamax.ajo.services.implementation;

import com.obamax.ajo.dto.RequestDTO;
import com.obamax.ajo.exceptions.BadRequestException;
import com.obamax.ajo.exceptions.ResourceNotFoundException;
import com.obamax.ajo.models.ContributionCycle;
import com.obamax.ajo.models.Member;
import com.obamax.ajo.models.Request;
import com.obamax.ajo.models.RequestStatusType;
import com.obamax.ajo.repositories.ContributionCycleRepository;
import com.obamax.ajo.repositories.MemberRepository;
import com.obamax.ajo.repositories.RequestRepository;
import com.obamax.ajo.services.RequestService;
import com.obamax.ajo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ContributionCycleRepository contributionCycleRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public Request makeRequest(RequestDTO requestDTO, Long cycleId, String email) {
        Optional<Member> member = memberRepository.findByEmailAddress(email);
        Optional<ContributionCycle> contributionCycle = contributionCycleRepository.findById(cycleId);

        Request request = new Request();
        if (member.isPresent() && contributionCycle.isPresent()) {
            request.setMember(member.get());
            request.setDateOfRequest(DateUtils.getCurrentTime());
            request.setContributionCycle(contributionCycle.get());
            request.setStatusOfRequest(RequestStatusType.PENDING);
            request.setRequestMessage(requestDTO.getRequestMessage());

            requestRepository.save(request);
            return request;
        } else {
            throw new BadRequestException("ContributionCycle Id not valid and Member not found.");
        }
    }

    @Override
    public List<Request> getAllCycleRequests(Long cycleId) {
        Optional<ContributionCycle> contributionCycle =  contributionCycleRepository.findById(cycleId);
        if(contributionCycle.isPresent()) {
            return requestRepository.findAllByContributionCycle(contributionCycle.get());
        } else {
            throw new ResourceNotFoundException("ContributionCycle Id not valid.");
        }
    }
}
