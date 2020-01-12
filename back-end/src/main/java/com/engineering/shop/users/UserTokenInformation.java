package com.engineering.shop.users;




import java.util.Collection;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTokenInformation {
	private long Id;
    private String email;
    private String firstName;
    private String lastName;
    private String privileges;
    private List<Role> roles;
    public long getId() {
    	return Id;
    }
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPrivileges() {
		return privileges;
	}
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
    

}




