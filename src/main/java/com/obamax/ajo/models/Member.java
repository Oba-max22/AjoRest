package com.obamax.ajo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @NotBlank(message = "last name can not be blank")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "first name can not be blank")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(min = 6, message = "password length must be more than 5")
    @Column(name = "password", nullable = false)
    private String password;

    @Email(message = "Email must have valid format")
    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @NotBlank(message="Please enter your phone number")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotBlank
    @Column(name = "date_joined", nullable = false)
    private String dateJoined;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Contribution> contributions;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Request> requests;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name="member_roles",
            joinColumns= {@JoinColumn(name="member_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private List<Role> roles;

    public Member(String lastName, String firstName, String password, String emailAddress, String phoneNumber, String dateJoined) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.dateJoined = dateJoined;
    }
}
