package com.engineering.shop.users;

import com.engineering.shop.common.Transformer;
import org.apache.tomcat.util.buf.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class UserToUserTokenInformationTransformer implements Transformer<User, UserTokenInformation> {
    @Override
    public UserTokenInformation transform(User user) {
        List<String> roles = user.getRoles().stream().map(Object::toString).collect(Collectors.toList());
        return UserTokenInformation.builder()
                .email(user.getEmail())
                .name(user.getFirstName() + " " + user.getLastName())
                .roles(StringUtils.join(roles, ','))
                .build();
    }
}
