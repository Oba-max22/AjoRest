package com.obamax.ajo.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User extends BaseEntity {

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

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name="users_roles",
            joinColumns= {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private List<Role> roles;
}
