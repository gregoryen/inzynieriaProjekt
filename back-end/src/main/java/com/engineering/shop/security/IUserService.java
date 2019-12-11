package com.engineering.shop.security;

import com.engineering.shop.users.User;
import com.engineering.shop.users.UserSignUpPOJO;

public interface IUserService {
    User registerNewUserAccount(UserSignUpPOJO account);
}
