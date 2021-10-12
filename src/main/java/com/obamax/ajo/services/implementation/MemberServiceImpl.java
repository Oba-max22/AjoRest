package com.obamax.ajo.services.implementation;

import com.obamax.ajo.exceptions.BadRequestException;
import com.obamax.ajo.exceptions.ResourceNotFoundException;
import com.obamax.ajo.models.Member;
import com.obamax.ajo.models.User;
import com.obamax.ajo.payload.request.MemberEditRequest;
import com.obamax.ajo.payload.request.RegisterMemberRequest;
import com.obamax.ajo.repositories.MemberRepository;
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
    private MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Member registration(RegisterMemberRequest registerMember) {
        if(memberRepository.existsByEmailAddress(registerMember.getEmailAddress())) {
            throw new BadRequestException("Error: Email is already taken!");
        }
        if(!(registerMember.getPassword().equals(registerMember.getConfirmPassword()))){
            throw new BadRequestException("Error: Password does not match");
        }
        if(!isValidPassword(registerMember.getPassword())){
            throw new BadRequestException("Error: Password must be between 8 and 20, must be an Alphabet or Number");
        }
        if(!isValidEmail(registerMember.getEmailAddress())){
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
    public Member editMember(Long memberId, MemberEditRequest memberRequest) {
        Optional<Member> member = memberRepository.findById(memberId);

        if (member.isPresent()) {
            member.get().setLastName(memberRequest.getLastName());
            member.get().setFirstName(memberRequest.getFirstName());
            member.get().setEmailAddress(memberRequest.getEmailAddress());
            member.get().setPhoneNumber(memberRequest.getPhoneNumber());
            saveMember(member.get());
            return member.get();
        } else {
            throw new ResourceNotFoundException("Member not found! Check id and try again." );
        }
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}