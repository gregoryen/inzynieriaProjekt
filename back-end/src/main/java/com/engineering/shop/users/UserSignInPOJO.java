package com.engineering.shop.users;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignInPOJO {
    private String email;
    private String password;
}
