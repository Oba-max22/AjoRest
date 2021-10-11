package com.obamax.ajo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "member")
public class Member extends BaseUser {

    @NotBlank(message="Please enter your phone number")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotBlank
    @Column(name = "date_joined", nullable = false)
    private String dateJoined;

    @JsonIgnore
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Contribution> contributions;

    @JsonIgnore
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
        super.lastName = lastName;
        super.firstName = firstName;
        super.password = password;
        super.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.dateJoined = dateJoined;
    }
}
