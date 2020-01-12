package com.engineering.shop.security;

import com.engineering.shop.users.User;
import com.engineering.shop.users.UserSignUpPOJO;

public interface IUserService {
    User registerNewUserAccount(UserSignUpPOJO account);
    void createPasswordResetTokenForUser(User user, String token);
    String validatePasswordResetToken(long id, String token);
    //User changeUserData(UserSignUpPOJO account);
}
