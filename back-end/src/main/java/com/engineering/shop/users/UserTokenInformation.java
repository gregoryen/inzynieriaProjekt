package com.engineering.shop.users;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTokenInformation {
    private String email;
    private String name;
    private String privileges;
    private String lastName;
    private String role;

}




