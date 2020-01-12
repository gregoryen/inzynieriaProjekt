package com.engineering.shop.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

import com.engineering.shop.users.Role;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtResponse implements Serializable {
    private String jwtToken;
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String privileges;
    //private Collection<Role> roles;
    
}
