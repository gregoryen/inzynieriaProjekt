package com.engineering.shop.users;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;


@Transactional
public interface RoleRepository extends CrudRepository<Role, Long> {



    List<Role> getAllByName(String name);
    Role getByName(String name);

    @Modifying
    @Query(value="delete from roles_privileges where role_id = ?1",nativeQuery = true)
    void deleteQuery1(long id);
    @Modifying
    @Query(value="insert into roles_privileges values (?1,(select id from privilege where name = ?2))",nativeQuery = true)
    void insertQuery1(long id, String roleName );

    @Override
    <S extends Role> S save(S role);
}
