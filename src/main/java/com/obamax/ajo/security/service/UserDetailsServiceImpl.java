package com.obamax.ajo.security.service;

import com.obamax.ajo.models.Member;
import com.obamax.ajo.models.User;
import com.obamax.ajo.services.MemberService;
import com.obamax.ajo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private MemberService memberService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userService.findUserByEmail(email);
        if (user.isPresent()) {
            return UserDetailsImpl.build(user.get());
        } else {
            Member member = memberService.findMemberByEmail(email);
            return UserDetailsImpl.build(member);
        }
    }
}