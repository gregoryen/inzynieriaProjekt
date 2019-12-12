package com.engineering.shop.users;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrivilegeRepository extends CrudRepository<Privilege,Long> {


    List<Privilege> getAllByName(String name);
    Privilege getByName(String name);

    @Override
    <S extends Privilege> S save(S privilege);
}
