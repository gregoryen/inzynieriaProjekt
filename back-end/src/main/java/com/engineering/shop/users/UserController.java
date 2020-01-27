package com.engineering.shop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "user/{id}")
    public User getUser(@PathVariable("id") long id) {

        return userRepository.getById(id);
    }

    @GetMapping(path ="getAllUsers")
    public Collection<?> getAllUsers(){
            return userRepository.getAllUsers();
    }
    @GetMapping(path="getUserPrivileges")
    public Collection<?> getUserPrivileges(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    @DeleteMapping(path="deleteUserByEmail")
    public void deleteUser(@RequestParam  String email){
        userRepository.deleteUserHelpQuery1(email);
        userRepository.deleteById(userRepository.getByEmail(email).getId());
    }
    @PostMapping(path="changeUserRole")
    public void changeUserRole(@RequestParam String roleName, @RequestParam  Long userID){

        userRepository.changeRoleQuery(roleName,userID);
    }

   // @Query ("SELECT name from privilege INNER JOIN roles_privileges ON privilege.id = roles_privileges.privilege_id INNER JOIN users_roles ON users_roles.role_id = roles_privileges.role_id")
    //public Collection<Privilege> getUserPrivileges(@PathVariable("id") long id)


}
