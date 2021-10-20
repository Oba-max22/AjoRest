package com.obamax.ajo.services.implementation;

import com.obamax.ajo.dto.RequestDTO;
import com.obamax.ajo.exceptions.BadRequestException;
import com.obamax.ajo.exceptions.ResourceNotFoundException;
import com.obamax.ajo.models.*;
import com.obamax.ajo.repositories.ContributionCycleRepository;
import com.obamax.ajo.repositories.MemberContributionCycleRepository;
import com.obamax.ajo.repositories.MemberRepository;
import com.obamax.ajo.repositories.RequestRepository;
import com.obamax.ajo.services.RequestService;
import com.obamax.ajo.utils.DateUtils;
import com.obamax.ajo.utils.SlotUtils;
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

    @Autowired
    private MemberContributionCycleRepository memberContributionCycleRepository;

    @Override
    public Request makeRequest(RequestDTO requestDTO, Long cycleId, String email) {
        Optional<Member> member = memberRepository.findByEmailAddress(email);
        Optional<ContributionCycle> contributionCycle = contributionCycleRepository.findById(cycleId);

        Request request = new Request();
        if (member.isPresent() && contributionCycle.isPresent()) {

            if(requestRepository.existsByMember(member.get())) {
                throw new BadRequestException("You have already made a request to " +
                        "join this ContributionCycle.");
            }

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

    @Override
    public List<Request> getAllMemberRequests(String email) {
        Optional<Member> member = memberRepository.findByEmailAddress(email);
        if(member.isPresent()) {
            return requestRepository.findAllByMember(member.get());
        } else {
            throw new ResourceNotFoundException("Member not found! Login with a Member account and try again.");
        }
    }

    @Override
    public void declineRequest(Long requestId) {
        Optional<Request> request = requestRepository.findById(requestId);
        if(request.isPresent()) {
            request.get().setStatusOfRequest(RequestStatusType.DECLINED);
            requestRepository.save(request.get());
        } else {
            throw new BadRequestException("Request Id not valid.");
        }
    }

    @Override
    public void approveRequest(Long requestId) {
        Optional<Request> request = requestRepository.findById(requestId);
        if(request.isPresent()) {
            addMemberToContributionCycle(request.get().getMember(), request.get().getContributionCycle());
            request.get().setStatusOfRequest(RequestStatusType.APPROVED);
            requestRepository.save(request.get());
        } else {
            throw new BadRequestException("Request Id not valid.");
        }
    }

    private void addMemberToContributionCycle(Member member, ContributionCycle contributionCycle) {
        MemberContributionCycle memberContributionCycle = new MemberContributionCycle();

        memberContributionCycle.setMember(member);
        memberContributionCycle.setContributionCycle(contributionCycle);

        if(SlotUtils.slotValue < 12) {
            memberContributionCycle.setSlot(SlotUtils.incrementSlotValue());
        } else {
            throw new BadRequestException("Slots full!");
        }

        memberContributionCycle.setAmountPaid(0.00);
        memberContributionCycle.setDateJoined(DateUtils.getCurrentTime());
        memberContributionCycle.setDateJoined("N/A");
        memberContributionCycle.setStatus(StatusType.NOT_PAID);

        memberContributionCycleRepository.save(memberContributionCycle);
    }
}
