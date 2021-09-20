package com.obamax.ajo.services;

import com.obamax.ajo.models.Member;
import com.obamax.ajo.payload.request.RegisterMemberRequest;

public interface MemberService {
    Member registration(RegisterMemberRequest registerMember);

    Member saveMember(Member member);
}
