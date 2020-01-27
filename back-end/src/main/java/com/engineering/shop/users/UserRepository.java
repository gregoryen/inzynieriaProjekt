package com.engineering.shop.users;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User getById(Long id);

    User getByEmail(String email);

    User getByFirstNameAndLastName(String firstName, String lastName);

    @Query(value = "select first_name, last_name, email, (select role.name from role where id = (select role_id from users_roles where users_roles.user_id = users.id)), users.id from users",nativeQuery = true)
    Collection<?> getAllUsers();

    @Transactional
    @Modifying
    @Query(value="update users_roles set role_id = (select id from role where name = ?1 ) where user_id = ?2", nativeQuery = true)
    void changeRoleQuery(String name, Long id);
}



