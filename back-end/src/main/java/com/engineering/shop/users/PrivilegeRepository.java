package com.engineering.shop.users;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface PrivilegeRepository extends CrudRepository<Privilege,Long> {


    List<Privilege> getAllByName(String name);
    Privilege getByName(String name);
    @Query(value = "select id, name from privilege where id IN (select privilege_id from roles_privileges where role_id = (select id from role where name = ?1))", nativeQuery = true)
    Collection<?> getAllByRoleName(String name);
    @Override
    <S extends Privilege> S save(S privilege);
}
