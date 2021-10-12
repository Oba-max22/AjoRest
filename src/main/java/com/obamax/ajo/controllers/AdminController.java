package com.obamax.ajo.controllers;

import com.obamax.ajo.dto.ContributionCycleDTO;
import com.obamax.ajo.exceptions.ResourceNotFoundException;
import com.obamax.ajo.models.ContributionCycle;
import com.obamax.ajo.models.Member;
import com.obamax.ajo.models.Role;
import com.obamax.ajo.models.RoleType;
import com.obamax.ajo.payload.request.MemberEditRequest;
import com.obamax.ajo.payload.request.RegisterMemberRequest;
import com.obamax.ajo.payload.response.MemberResponse;
import com.obamax.ajo.repositories.RoleRepository;
import com.obamax.ajo.services.ContributionCycleService;
import com.obamax.ajo.services.MemberService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {

    private final ContributionCycleService contributionCycleService;
    private final MemberService memberService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public AdminController(ContributionCycleService contributionCycleService,
                           MemberService memberService,
                           RoleRepository roleRepository, ModelMapper modelMapper) {
        this.contributionCycleService = contributionCycleService;
        this.memberService = memberService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/admin/edit-member-details/{memberId}")
    @ApiOperation(value = "Allows admin to edit member details")
    public ResponseEntity<MemberResponse> adminEditMemberDetails (@Valid @RequestBody MemberEditRequest memberRequest,
                                                                  @PathVariable Long memberId) {
        Member member = memberService.editMember(memberId, memberRequest);
        return new ResponseEntity<>(MemberResponse.build(member), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/get-all-members")
    @ApiOperation("Admin can view a list of members on the platform")
    public ResponseEntity<List<Member>> viewListOfMember() {
        List<Member> memberList = memberService.getAllMembers();
        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/create-cycle")
    @ApiOperation(value = "Create a new contribution cycle")
    public ResponseEntity<ContributionCycleDTO> createContributionCycle(@Valid @RequestBody
                                                                        ContributionCycleDTO contributionCycleRequest) {
        ContributionCycle contributionCycle = contributionCycleService.createCycle(contributionCycleRequest);
        ContributionCycleDTO contributionCycleResponse = modelMapper.map(contributionCycle, ContributionCycleDTO.class);
        return new ResponseEntity<>(contributionCycleResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/edit-cycle/{cycleId}")
    @ApiOperation(value = "Edit contribution cycle constraints")
    public ResponseEntity<ContributionCycleDTO> editContributionCycle(@Valid @RequestBody
                                                                      ContributionCycleDTO contributionCycleRequest,
                                                                      @PathVariable Long cycleId) {
        ContributionCycle contributionCycle = contributionCycleService.editCycle(contributionCycleRequest, cycleId);
        ContributionCycleDTO contributionCycleResponse = modelMapper.map(contributionCycle, ContributionCycleDTO.class);
        return new ResponseEntity<>(contributionCycleResponse, HttpStatus.OK);
    }

    // TODO - Endpoint for Admin to view list of requests in contribution cycle.



    // TODO - Endpoint for Admin to approve member request to join contribution cycle.



    // TODO - Endpoint for Admin to decline member request to join contribution cycle.



    // TODO - Endpoint for Admin to view list of members in contribution cycle.



    // TODO - Endpoint for Admin to view collection order in a contribution cycle.



    // TODO - Endpoint for Admin to start cycle.



    // TODO - Endpoint for Admin to pay to beneficiary in a contribution cycle.



}
