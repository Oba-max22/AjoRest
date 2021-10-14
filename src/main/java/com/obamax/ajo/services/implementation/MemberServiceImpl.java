package com.obamax.ajo.services.implementation;

import com.obamax.ajo.exceptions.ResourceNotFoundException;
import com.obamax.ajo.models.Member;
import com.obamax.ajo.payload.request.MemberEditRequest;
import com.obamax.ajo.repositories.ContributionCycleRepository;
import com.obamax.ajo.repositories.MemberRepository;
import com.obamax.ajo.repositories.RequestRepository;
import com.obamax.ajo.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

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
    public Member findMemberById(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            throw new ResourceNotFoundException("Incorrect parameter :: member Id " + memberId + " does not exist");
        }
        return member.get();
    }

}
