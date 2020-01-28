package com.engineering.shop.users;

import com.engineering.shop.common.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Component
public class UserSignUpPOJOToUserTransformer implements Transformer<UserSignUpPOJO, User> {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PrivilegeRepository privilegeRepository;
    @Override

    public User transform(UserSignUpPOJO pojo) {

        Privilege privilege = createPrivilegeIfNotFound("PRIVILEGE_USER");
        Privilege privilege2 = createPrivilegeIfNotFound("PRIVILEGE_COMMUNICATOR_CLIENT");
        Privilege privilege3 =createPrivilegeIfNotFound("PRIVILEGE_COMMUNICATOR_ADMIN");
        Privilege privilege4 = createPrivilegeIfNotFound("PRIVILEGE_ADMIN");
        Privilege privilege5 = createPrivilegeIfNotFound("PRIVILEGE_PRODUCT_MANAGER");
        Role role2 = createRoleIfNotFound("ROLE_ADMIN",Arrays.asList(privilege4,privilege5,privilege3));
        Role role = createRoleIfNotFound("ROLE_USER",Arrays.asList(privilege,privilege2));
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
