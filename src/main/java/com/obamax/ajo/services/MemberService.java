package com.obamax.ajo.services;

import com.obamax.ajo.models.Member;
import com.obamax.ajo.payload.request.MemberEditRequest;

import java.util.List;


public interface MemberService {
    Member saveMember(Member member);
    Member findMemberByEmail(String email);
    Member editMember(Member member, MemberEditRequest memberRequest);
    List<Member> getAllMembers();
    Member findMemberById(Long memberId);
}
