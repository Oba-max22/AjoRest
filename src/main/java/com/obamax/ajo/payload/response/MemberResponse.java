package com.obamax.ajo.payload.response;

import com.obamax.ajo.models.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MemberResponse {

    private String message;

    private long Id;

    private String lastName;

    private String firstName;

    private  String emailAddress;

    private String phoneNumber;

    private String dateJoined;

    public static MemberResponse build(Member member){
        return new MemberResponse (
                "Registration Successful",
                member.getId(),
                member.getLastName(),
                member.getFirstName(),
                member.getEmailAddress(),
                member.getPhoneNumber(),
                member.getDateJoined()
        );
    }
}