package com.obamax.ajo.services;

import com.obamax.ajo.dto.RequestDTO;
import com.obamax.ajo.models.Member;
import com.obamax.ajo.models.Request;
import com.obamax.ajo.payload.request.MemberEditRequest;
import com.obamax.ajo.payload.request.RegisterMemberRequest;
import java.util.List;


public interface MemberService {
    Member registration(RegisterMemberRequest registerMember);
    Member saveMember(Member member);
    Member findMemberByEmail(String email);
    Member editMember(Member member, MemberEditRequest memberRequest);
    List<Member> getAllMembers();
    Request makeRequest(RequestDTO request, Long cycleId, String email);
    Member findMemberById(Long memberId);
}
