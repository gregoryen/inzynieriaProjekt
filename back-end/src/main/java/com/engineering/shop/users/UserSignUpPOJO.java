package com.engineering.shop.users;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignUpPOJO {
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
