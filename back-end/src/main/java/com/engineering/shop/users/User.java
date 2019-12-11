package com.engineering.shop.users;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import java.util.Set;

@AllArgsConstructor
@Data
@Builder(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "USERS")
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    private String password;
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Singular
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    Set<Role> roles;
    public User() {
    }
}



