package com.obamax.ajo.services;

import com.obamax.ajo.models.Member;
import com.obamax.ajo.payload.request.MemberEditRequest;
import com.obamax.ajo.payload.request.RegisterMemberRequest;
import java.util.List;


public interface MemberService {
    Member registration(RegisterMemberRequest registerMember);
    Member saveMember(Member member);
    Member findMemberByEmail(String email);
    Member editMember(Long memberId, MemberEditRequest memberRequest);
    List<Member> getAllMembers();
}
