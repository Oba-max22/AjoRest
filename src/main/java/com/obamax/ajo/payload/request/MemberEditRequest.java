package com.obamax.ajo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberEditRequest {

    private String lastName;

    private String firstName;

    private String emailAddress;

    private String phoneNumber;
}
