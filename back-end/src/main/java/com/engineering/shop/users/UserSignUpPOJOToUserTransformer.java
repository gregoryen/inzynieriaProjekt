package com.engineering.shop.users;

import com.engineering.shop.common.Transformer;

import lombok.Builder;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Component
@Builder
@Getter
public class UserSignUpPOJOToUserTransformer implements Transformer<UserSignUpPOJO, User> {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PrivilegeRepository privilegeRepository;
    @Override

    public User transform(UserSignUpPOJO pojo) {

        Privilege privilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Role role = createRoleIfNotFound("ROLE_USER",Arrays.asList(privilege));
        return User.builder()
        	.email(pojo.getEmail())
        	.firstName(pojo.getFirstName())
        	.lastName(pojo.getLastName())
        	.roles(Arrays.asList(role))
        	.password(pojo.getPassword())
        	.build();
    }
    @Transactional
     Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.getByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.getByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
