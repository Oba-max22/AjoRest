package com.obamax.ajo.services;

import com.obamax.ajo.models.Member;
import com.obamax.ajo.models.User;
import com.obamax.ajo.payload.request.RegisterMemberRequest;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);
    Member registration(RegisterMemberRequest registerMember);
}
