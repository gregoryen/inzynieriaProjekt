package com.engineering.shop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

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
    @PostMapping(path="createRoleByName")
    public Long createRoleByName(@RequestParam String roleName){
        return createRoleIfNotFound(roleName).getId();
    }

    @DeleteMapping (path="deleteRole")
    public void deleteRole(@RequestParam String roleName){
        roleRepository.updateQueryChangeUserRoles(roleName);
        roleRepository.deleteQuery1(roleRepository.getByName(roleName).getId());
        roleRepository.deleteById(roleRepository.getByName(roleName).getId());
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
    Role createRoleIfNotFound(String name) {

        Role role = roleRepository.getByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(Arrays.asList(createPrivilegeIfNotFound("READ_PRIVILEGE")));
            roleRepository.save(role);
        }
        return role;
    }
}
