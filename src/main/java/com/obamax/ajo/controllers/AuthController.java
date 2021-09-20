package com.obamax.ajo.controllers;

import com.obamax.ajo.exceptions.ResourceNotFoundException;
import com.obamax.ajo.models.Member;
import com.obamax.ajo.models.Role;
import com.obamax.ajo.models.RoleType;
import com.obamax.ajo.payload.request.LoginRequest;
import com.obamax.ajo.payload.request.RegisterMemberRequest;
import com.obamax.ajo.payload.response.LoginResponse;
import com.obamax.ajo.payload.response.MemberResponse;
import com.obamax.ajo.repositories.RoleRepository;
import com.obamax.ajo.security.jwt.JwtUtils;
import com.obamax.ajo.security.service.UserDetailsServiceImpl;
import com.obamax.ajo.services.MemberService;
import com.obamax.ajo.services.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailService;
    private final JwtUtils jwtUtils;
    private final MemberService memberService;
    private final RoleRepository roleRepository;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailService,
                          JwtUtils jwtUtils, MemberService memberService,
                          RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtils = jwtUtils;
        this.memberService = memberService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/admin/create-member")
    @ApiOperation(value = "Create a new member")
    public MemberResponse register(@Valid @RequestBody RegisterMemberRequest registerMember) {
        Member member = memberService.registration(registerMember);
        List<Role> roles = new ArrayList<>();
        Role memberRole = roleRepository.findRoleByType(RoleType.MEMBER)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
        roles.add(memberRole);
        member.setRoles(roles);
        Member savedMember = memberService.saveMember(member);
        return MemberResponse.build(savedMember);
    }

    @PostMapping("/admin/login")
    @ApiOperation(value = "Logs in an Admin")
    public ResponseEntity<?> doLogin(@Valid @RequestBody LoginRequest request){
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmailAddress(), request.getPassword()
                        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailService.loadUserByUsername(request.getEmailAddress());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        final String jwtToken = jwtUtils.generateToken(userDetails);

        LoginResponse response = new LoginResponse(jwtToken, roles);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
