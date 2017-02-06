package com.global.security;

import java.util.HashSet;
import java.util.Set;

public class Role {
	

	private String id;
	private String name;
	private Set<UserRole> userRoles = new HashSet<UserRole>();
	
	public Role() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	
}
