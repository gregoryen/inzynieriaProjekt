package com.engineering.shop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
@RestController
public class RoleController {

    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository,PrivilegeRepository privilegeRepository) {
    this.roleRepository = roleRepository;
    this.privilegeRepository = privilegeRepository;
    }

    @PostMapping(path="changeRolePrivileges")
    public void changeRolePrivileges(String privilegesArr, long roleID){
        String query="";
        query = "delete from roles_privileges where role_id = "+roleID;
        roleRepository.deleteQuery1(roleID);

        String[] arr = privilegesArr.split("-");
        for(int i=0;i<arr.length;i++)
        {
            query = "insert into roles_privileges values ("+roleID+" ,"+" (select id from privilege where name = "+arr[i]+"))";
            roleRepository.insertQuery1(roleID, arr[i]);

        }

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
