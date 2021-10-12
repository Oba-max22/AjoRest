package com.obamax.ajo.controllers;

import com.obamax.ajo.payload.request.LoginRequest;
import com.obamax.ajo.payload.response.LoginResponse;
import com.obamax.ajo.repositories.RoleRepository;
import com.obamax.ajo.security.jwt.JwtUtils;
import com.obamax.ajo.security.service.UserDetailsServiceImpl;
import com.obamax.ajo.services.MemberService;
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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailService,
                          JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    @ApiOperation(value = "Admin and Member Login")
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
