package com.engineering.shop.users;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RoleRepository extends CrudRepository<Role, Long> {



    List<Role> getAllByName(String name);
    Role getByName(String name);

    @Override
    <S extends Role> S save(S role);
}
