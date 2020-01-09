package com.engineering.shop.users;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class RoleController {

    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository,PrivilegeRepository privilegeRepository) {
    this.roleRepository = roleRepository;
    this.privilegeRepository = privilegeRepository;
    }

    //PrivilegeController privilegeController = new PrivilegeController(privilegeRepository);
//    public Role createRoleIfNotFound(String name)
//    {
//        List<Role> list = roleRepository.getAllByName(name);
//        if(list.size()<1)
//        {
//            Set<Privilege> set = Set.of(privilegeController.createPrivilegeIfNotFound("READ_PRIVILEGE"));
//            return new Role(name, set);
//        }
//        else
//            return roleRepository.getByName(name);
//    }

}
