package com.engineering.shop.users;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;


import javax.persistence.*;
import java.util.Collection;
import java.util.Set;


@Data
@Entity
public class Role {




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;
    public Role(String name) {
        this.name = name;
    }
    public Role(String name, Collection<Privilege> privileges) {
        this.name = name;
        this.privileges = privileges;
    }
    public Role()
    {

    }

    @Override
    public String toString() {
        return name;


    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Collection<User> getUsers() {
		return users;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	public Collection<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Collection<Privilege> privileges) {
		this.privileges = privileges;
	}
    
    
}

