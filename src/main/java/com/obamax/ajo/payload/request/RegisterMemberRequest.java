package com.obamax.ajo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterMemberRequest {

    @NotNull(message = "last name cannot be empty")
    private String lastName;

    @NotNull(message = "first name cannot be empty")
    private String firstName;

    @NotNull(message = "email cannot be empty")
    @Email(message = "must be email")
    private String emailAddress;

    @NotNull(message = "password cannot be empty")
    @Size(min = 6, max = 24)
    private String password;

    @NotNull(message = "password cannot be empty")
    @Size(min = 6, max = 24)
    private String confirmPassword;

    private String phoneNumber;
}
