package com.engineering.shop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@RestController
public class PrivilegeController {

    public PrivilegeRepository privilegeRepository;
    public RoleRepository roleRepository;
    @Autowired
    public PrivilegeController(PrivilegeRepository privilegeRepository, RoleRepository roleRepository) {
this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
    }
    @GetMapping(path="getPrivilegesByRole")
    Collection<?> getPrivilegesByRole(String name)
    {
        return privilegeRepository.getAllByRoleName(name);
    }
//        Privilege createPrivilegeIfNotFound(String name)
//        {
//            List<Privilege> list = privilegeRepository.getAllByName(name);
//                if(list.size()<1)
//                {
//                    return new Privilege(name);
//                }
//                else
//                    return privilegeRepository.getByName(name);
//        }

}
