package com.obamax.ajo.services.implementation;

import com.obamax.ajo.dto.RequestDTO;
import com.obamax.ajo.exceptions.BadRequestException;
import com.obamax.ajo.exceptions.ResourceNotFoundException;
import com.obamax.ajo.models.ContributionCycle;
import com.obamax.ajo.models.Member;
import com.obamax.ajo.models.Request;
import com.obamax.ajo.models.RequestStatusType;
import com.obamax.ajo.payload.request.MemberEditRequest;
import com.obamax.ajo.payload.request.RegisterMemberRequest;
import com.obamax.ajo.repositories.ContributionCycleRepository;
import com.obamax.ajo.repositories.MemberRepository;
import com.obamax.ajo.repositories.RequestRepository;
import com.obamax.ajo.services.MemberService;
import com.obamax.ajo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ContributionCycleRepository contributionCycleRepository;
    @Autowired
    private RequestRepository requestRepository;

    @Override
    public Member registration(RegisterMemberRequest registerMember) {
        if (memberRepository.existsByEmailAddress(registerMember.getEmailAddress())) {
            throw new BadRequestException("Error: Email is already taken!");
        }
        if (!(registerMember.getPassword().equals(registerMember.getConfirmPassword()))) {
            throw new BadRequestException("Error: Password does not match");
        }
        if (!isValidPassword(registerMember.getPassword())) {
            throw new BadRequestException("Error: Password must be between 8 and 20, must be an Alphabet or Number");
        }
        if (!isValidEmail(registerMember.getEmailAddress())) {
            throw new BadRequestException("Error: Email must be valid");
        }

        return new Member(
                registerMember.getLastName(),
                registerMember.getFirstName(),
                passwordEncoder.encode(registerMember.getPassword()),
                registerMember.getEmailAddress(),
                registerMember.getPhoneNumber(),
                DateUtils.getCurrentTime()
        );
    }

    private boolean isValidEmail(String emailAddress) {
        String regex = "^(.+)@(\\w+)\\.(\\w+)$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        if (emailAddress == null) {
            throw new BadRequestException("Error: Email cannot be null");
        }
        Matcher m = p.matcher(emailAddress);
        return m.matches();
    }

    private boolean isValidPassword(String password) {
        String regex = "^(([0-9]|[a-z]|[A-Z]|[@])*){8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            throw new BadRequestException("Error: Password cannot be null");
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    @Override
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member findMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmailAddress(email);
        if (member.isEmpty()) {
            throw new ResourceNotFoundException("Incorrect parameter :: email " + email + " does not exist");
        }
        return member.get();
    }

    @Override
    public Member editMember(Member member, MemberEditRequest memberRequest) {

        if (member != null) {
            member.setLastName(memberRequest.getLastName());
            member.setFirstName(memberRequest.getFirstName());
            member.setEmailAddress(memberRequest.getEmailAddress());
            member.setPhoneNumber(memberRequest.getPhoneNumber());
            saveMember(member);
            return member;
        } else {
            throw new ResourceNotFoundException("Member not found! Check id and try again.");
        }
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

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
    public Member findMemberById(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            throw new ResourceNotFoundException("Incorrect parameter :: member Id " + memberId + " does not exist");
        }
        return member.get();
    }

}
