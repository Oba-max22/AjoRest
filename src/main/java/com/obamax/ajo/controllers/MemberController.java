package com.obamax.ajo.controllers;

import com.obamax.ajo.models.Member;
import com.obamax.ajo.payload.request.MemberEditRequest;
import com.obamax.ajo.payload.response.MemberResponse;
import com.obamax.ajo.services.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PutMapping("/member/edit-member-details/{memberId}")
    @ApiOperation(value = "Member can edit their details")
    public ResponseEntity<MemberResponse> editMemberDetails (@Valid @RequestBody MemberEditRequest memberRequest, @PathVariable Long memberId) {
        Member member = memberService.editMember(memberId, memberRequest);
        MemberResponse memberResponse = MemberResponse.build(member);
        memberResponse.setMessage("Member details edited successfully");
        return new ResponseEntity<>(memberResponse, HttpStatus.OK);
    }

    // TODO - Endpoint for member to make request to join a contribution cycle.



    // TODO - Endpoint for member to view request status.



    // TODO - Endpoint for member to view details of contribution cycle.



    // TODO - Endpoint for member to view list of members in contribution cycle.



    // TODO - Endpoint for member to make monthly contribution.



}
